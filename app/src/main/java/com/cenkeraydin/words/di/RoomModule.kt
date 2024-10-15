package com.cenkeraydin.words.di

import android.content.Context
import androidx.room.Room
import com.cenkeraydin.words.data.room.WordDatabase
import com.cenkeraydin.words.data.room.WordsDAO
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

object RoomModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): WordDatabase {
        return Room.databaseBuilder(
            context,
            WordDatabase::class.java,
            "word_database"
        ).build()
    }

    @Provides
    fun provideWordDao(database: WordDatabase): WordsDAO {
        return database.wordDao()
    }
}