package com.natife.myapplication.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Gif(
    @PrimaryKey
    val id: String,
    val url: String,
    val isRemoved: Boolean = false
)