package com.natife.myapplication.repository

import com.natife.myapplication.data.dto.GifDto

interface GifRepository {
    suspend fun fetchGifs(query: String, limit: Int = 25, offset: Int = 0): List<String>
}