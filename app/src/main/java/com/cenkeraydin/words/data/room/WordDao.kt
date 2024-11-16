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

    @Query("SELECT * FROM words WHERE isLearned = 0 AND userId = :userId") // Exclude learned words for a specific user
    fun getAllWords(userId: String): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<Word>)

    @Query("SELECT * FROM words WHERE isLearned = 1 AND userId = :userId")
    fun getLearnedWords(userId: String): Flow<List<Word>>

    @Update
    suspend fun updateWord(word: Word)

    @Query("DELETE FROM words WHERE userId = :userId")
    suspend fun deleteAllWords(userId: String)

    @Delete
    suspend fun deleteWord(word: Word)

    @Query("SELECT * FROM words WHERE englishWord = :englishWord AND userId = :userId LIMIT 1")
    suspend fun getWordByEnglish(englishWord: String, userId: String): Word?
}
