package com.cenkeraydin.words

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cenkeraydin.words.data.model.Word
import com.cenkeraydin.words.data.repos.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.asLiveData
import com.cenkeraydin.words.data.model.WordList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStreamReader

@HiltViewModel
class WordViewModel @Inject constructor(
    private val repository: WordRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val words: LiveData<List<Word>> = repository.getAllWords().asLiveData()

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

    init {
        viewModelScope.launch {
            clearWordsAndReload()
        }
    }

    private suspend fun clearWordsAndReload() {
        repository.deleteAllWords()

        val jsonString = loadWordsFromJson()
        val wordList = parseWordsFromJson(jsonString)

        for (word in wordList) {
            val existingWord = repository.getWordByEnglish(word.englishWord)
            if (existingWord == null) {
                repository.insertWords(listOf(word))
            }
        }
    }

    suspend fun addWord(word: Word) {
        val existingWord = repository.getWordByEnglish(word.englishWord)
        if (existingWord == null) {
            repository.insertWords(listOf(word))
        }
    }

    fun deleteWord(word: Word) {
        viewModelScope.launch {
            repository.deleteWord(word)
        }
    }
}

