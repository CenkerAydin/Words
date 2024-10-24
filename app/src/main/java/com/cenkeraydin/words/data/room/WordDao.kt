package com.cenkeraydin.words.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cenkeraydin.words.data.model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM words WHERE isLearned = 0") // Exclude learned words
    fun getAllWords(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<Word>)

    @Query("SELECT * FROM words WHERE isLearned = 1")
    fun getLearnedWords(): Flow<List<Word>>

    @Update
    suspend fun updateWord(word: Word)


    @Query("DELETE FROM words")
    suspend fun deleteAllWords()

    @Delete
    suspend fun deleteWord(word: Word)

    @Query("SELECT * FROM words WHERE englishWord = :englishWord LIMIT 1")
    suspend fun getWordByEnglish(englishWord: String): Word?
}
