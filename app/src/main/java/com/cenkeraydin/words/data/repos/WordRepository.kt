package com.cenkeraydin.words.data.repos

import com.cenkeraydin.words.data.model.Word
import com.cenkeraydin.words.data.room.WordDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WordRepository @Inject constructor(private val wordDao: WordDao) {

    fun getAllWords(userId: String): Flow<List<Word>> = wordDao.getAllWords(userId)

    fun getLearnedWords(userId: String): Flow<List<Word>> = wordDao.getLearnedWords(userId)

    suspend fun updateWord(word: Word) {
        wordDao.updateWord(word)
    }

    suspend fun insertWords(words: List<Word>) {
        wordDao.insertWords(words)
    }

    suspend fun deleteAllWords(userId: String) {
        wordDao.deleteAllWords(userId)
    }

    suspend fun deleteWord(word: Word) {
        wordDao.deleteWord(word)
    }

    suspend fun getWordByEnglish(englishWord: String, userId: String): Word? {
        return wordDao.getWordByEnglish(englishWord, userId)
    }
}

