package com.cenkeraydin.words.di

import android.content.Context
import androidx.room.Room
import com.cenkeraydin.words.data.room.WordDao
import com.cenkeraydin.words.data.room.WordDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
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
    @Singleton
    fun provideWordDao(database: WordDatabase): WordDao {
        return database.wordDao()
    }
}