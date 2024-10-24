package com.cenkeraydin.words.data.repos

import com.cenkeraydin.words.data.model.Word
import com.cenkeraydin.words.data.room.WordDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WordRepository @Inject constructor(private val wordDao: WordDao) {

    fun getAllWords(): Flow<List<Word>> = wordDao.getAllWords()

    fun getLearnedWords(): Flow<List<Word>> = wordDao.getLearnedWords()

    suspend fun updateWord(word: Word) {
        wordDao.updateWord(word)
    }

    suspend fun insertWords(words: List<Word>) {
        wordDao.insertWords(words)
    }

    suspend fun deleteAllWords() {
        wordDao.deleteAllWords() // Tüm kelimeleri sil
    }

    suspend fun deleteWord(word: Word) {
        wordDao.deleteWord(word)
    }
    suspend fun getWordByEnglish(englishWord: String): Word? {
        return wordDao.getWordByEnglish(englishWord)
    }
}
