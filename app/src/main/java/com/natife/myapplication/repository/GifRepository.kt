package com.natife.myapplication.repository

import android.graphics.Bitmap
import androidx.paging.PagingData
import com.natife.myapplication.data.dto.GifDto
import com.natife.myapplication.room.Gif
import com.natife.myapplication.utils.GifsPage
import kotlinx.coroutines.flow.Flow

interface GifRepository {
//    suspend fun fetchGifs(query: String, limit: Int = 25, offset: Int = 0): List<String>

     fun fetchGifs(query: String, limit: Int = 25, offset: Int = 0): Flow<PagingData<String>>
     suspend fun fetchGifsFromLocalStorage(): List<Bitmap>
}