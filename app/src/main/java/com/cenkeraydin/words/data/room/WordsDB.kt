package com.cenkeraydin.words.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cenkeraydin.words.data.model.Word

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
}