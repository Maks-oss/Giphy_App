package com.natife.myapplication.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.natife.myapplication.fileprocessor.FileProcessor
import com.natife.myapplication.paging.GifSource
import com.natife.myapplication.retrofit.GifService
import kotlinx.coroutines.flow.Flow

class GifRepositoryImpl(
    private val gifService: GifService,
    private val fileProcessor: FileProcessor
) : GifRepository {
    //    override suspend fun fetchGifs(query: String, limit: Int, offset: Int): List<String> {
//        val gifDto = gifService.getGifsResponse(query = query, limit = limit, offset =  offset).also {
//            Log.d("TAG", "fetchGifs: ${it.raw().request.url} ${it.body()}")
//        }.body()
//        if (gifDto?.data == null) return emptyList()
//        return GifsMapper.convertGifDtoToGifList(gifDto)
//    }
    override fun fetchGifs(query: String, limit: Int, offset: Int): Flow<PagingData<String>> {
        return Pager(config = PagingConfig(pageSize = GifSource.ITEMS_PER_PAGE)) {
            GifSource(gifService, fileProcessor,query)
        }.flow
    }
}