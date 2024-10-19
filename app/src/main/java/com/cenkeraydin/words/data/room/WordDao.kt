package com.cenkeraydin.words.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cenkeraydin.words.data.model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM words ORDER BY englishWord ASC")
    fun getAllWords(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<Word>)

    @Query("DELETE FROM words") // Kelimelerin kaydedildiği tablo adı
    suspend fun deleteAllWords()

    @Delete
    suspend fun deleteWord(word: Word)

    @Query("SELECT * FROM words WHERE englishWord = :englishWord LIMIT 1")
    suspend fun getWordByEnglish(englishWord: String): Word?
}
