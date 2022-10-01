package com.natife.myapplication.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Gif::class], version = 2)
abstract class GifDatabase:RoomDatabase() {
    abstract fun gifDao(): GifDao
}