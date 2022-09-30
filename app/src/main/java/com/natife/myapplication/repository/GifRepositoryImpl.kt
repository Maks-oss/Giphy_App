package com.natife.myapplication.repository

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.natife.myapplication.paging.GifSource
import com.natife.myapplication.retrofit.GifService
import com.natife.myapplication.utils.GifsMapper
import com.natife.myapplication.utils.GifsPage
import kotlinx.coroutines.flow.Flow

class GifRepositoryImpl(private val gifService: GifService) : GifRepository {
    //    override suspend fun fetchGifs(query: String, limit: Int, offset: Int): List<String> {
//        val gifDto = gifService.getGifsResponse(query = query, limit = limit, offset =  offset).also {
//            Log.d("TAG", "fetchGifs: ${it.raw().request.url} ${it.body()}")
//        }.body()
//        if (gifDto?.data == null) return emptyList()
//        return GifsMapper.convertGifDtoToGifList(gifDto)
//    }
    override fun fetchGifs(query: String, limit: Int, offset: Int): Flow<PagingData<String>> {
//        val gifDto =
//            gifService.getGifsResponse(query = query, limit = limit, offset = offset).also {
//                Log.d("TAG", "fetchGifs: ${it.raw().request.url} ${it.body()}")
//            }.body()
//        if (gifDto?.data == null) return GifsPage(page = 0,emptyList())
//        return GifsPage(page = offset,GifsMapper.convertGifDtoToGifList(gifDto))
        return Pager(config = PagingConfig(pageSize = GifSource.ITEMS_PER_PAGE)) {
            GifSource(gifService, query)
        }.flow
    }
}