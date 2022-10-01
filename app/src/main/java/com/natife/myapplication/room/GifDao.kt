package com.natife.myapplication.room

import androidx.room.*

@Dao
interface GifDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGifs(gifs:List<Gif>)

    @Query("UPDATE gif SET isRemoved = :isRemoved WHERE id = :gifId ")
    suspend fun updateGif(gifId:String, isRemoved:Boolean)

    @Delete
    suspend fun deleteGif(gif: Gif)

    @Query("SELECT * FROM gif")
    suspend fun getAllGifs(): List<Gif>

    @Query("SELECT * FROM gif WHERE id = :id")
    fun getGifById(id:String): Gif?
}