package com.cenkeraydin.words

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cenkeraydin.words.data.model.Word
import com.cenkeraydin.words.data.repos.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.asLiveData
import com.cenkeraydin.words.data.model.WordList
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStreamReader

@HiltViewModel
class WordViewModel @Inject constructor(
    private val repository: WordRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val userId: String? = FirebaseAuth.getInstance().currentUser?.uid

    val words: LiveData<List<Word>> = userId?.let { repository.getAllWords(it).asLiveData() } ?: MutableLiveData()
    val learnedWords: LiveData<List<Word>> = userId?.let { repository.getLearnedWords(it).asLiveData() } ?: MutableLiveData()

    fun markAsLearned(word: Word) {
        viewModelScope.launch {
            word.isLearned = true
            repository.updateWord(word)  // Update word in database
        }
    }

    fun markAsUnlearned(word: Word) {
        viewModelScope.launch {
            word.isLearned = false
            repository.updateWord(word)  // Update word in database
        }
    }
    private fun loadWordsFromJson(): String {
        val inputStream = context.resources.openRawResource(R.raw.words)
        val reader = InputStreamReader(inputStream)
        return reader.readText()
    }

    private fun parseWordsFromJson(jsonString: String): List<Word> {
        val gson = Gson()
        val wordListType = object : TypeToken<WordList>() {}.type
        val wordList = gson.fromJson<WordList>(jsonString, wordListType)

        return wordList.words.map { word ->
            word.copy(
                englishWord = word.englishWord.replaceFirstChar { it.uppercase() },
                turkishWord = word.turkishWord.replaceFirstChar { it.uppercase() }
            )
        }
    }



    private suspend fun clearWordsAndReload() {
        userId?.let { uid ->
            repository.deleteAllWords(uid)

            val jsonString = loadWordsFromJson()
            val wordList = parseWordsFromJson(jsonString)

            for (word in wordList) {
                val existingWord = repository.getWordByEnglish(word.englishWord, uid)
                if (existingWord == null) {
                    repository.insertWords(listOf(word))
                }
            }
        }
    }

    fun addWord(word: Word) {
        viewModelScope.launch {
            userId?.let { uid ->
                val existingWord = repository.getWordByEnglish(word.englishWord, uid)
                if (existingWord == null) {
                    repository.insertWords(listOf(word.copy(userId = uid)))
                }
            }
        }
    }

    fun deleteWord(word: Word) {
        viewModelScope.launch {
            repository.deleteWord(word)
        }
    }
}

