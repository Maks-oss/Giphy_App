package com.natife.myapplication.repository

import com.natife.myapplication.retrofit.GifService
import com.natife.myapplication.utils.GifsMapper

class GifRepositoryImpl(private val gifService: GifService) : GifRepository {
    override suspend fun fetchGifs(query: String, limit: Int, offset: Int): List<String> {
        val gifDto = gifService.getGifsResponse(query, limit, offset).body() ?: return emptyList()
        return GifsMapper.convertGifDtoToGifList(gifDto)
    }
}