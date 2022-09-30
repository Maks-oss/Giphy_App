package com.natife.myapplication.repository

import android.util.Log
import com.natife.myapplication.retrofit.GifService
import com.natife.myapplication.utils.GifsMapper

class GifRepositoryImpl(private val gifService: GifService) : GifRepository {
    override suspend fun fetchGifs(query: String, limit: Int, offset: Int): List<String> {
        val gifDto = gifService.getGifsResponse(query = query, limit = limit, offset =  offset).also {
            Log.d("TAG", "fetchGifs: ${it.raw().request.url} ${it.body()}")
        }.body()
        if (gifDto?.data == null) return emptyList()
        return GifsMapper.convertGifDtoToGifList(gifDto)
    }
}