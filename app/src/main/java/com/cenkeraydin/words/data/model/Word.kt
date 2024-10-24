package com.cenkeraydin.words.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val englishWord: String,
    val turkishWord: String,
    var isLearned: Boolean = false
)