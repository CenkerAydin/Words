package com.cenkeraydin.words.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cenkeraydin.words.data.model.Word

@Dao
interface WordsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Word)

    @Delete
    suspend fun deleteWord(word: Word)

    @Query("SELECT * FROM words ORDER BY id ASC")
    fun getAllWords(): List<Word>

    @Query("SELECT * FROM words WHERE englishWord LIKE :searchQuery OR turkishWord LIKE :searchQuery")
    fun searchWords(searchQuery: String): List<Word>
}