package com.natife.myapplication.repository

import android.graphics.Bitmap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.natife.myapplication.fileprocessor.FileProcessor
import com.natife.myapplication.paging.GifSource
import com.natife.myapplication.retrofit.GifService
import com.natife.myapplication.room.Gif
import com.natife.myapplication.room.GifDao
import com.natife.myapplication.utils.SavedGif
import kotlinx.coroutines.flow.Flow

class GifRepositoryImpl(
    private val gifService: GifService,
    private val fileProcessor: FileProcessor,
    private val gifDao: GifDao
) : GifRepository {

    override fun fetchGifs(query: String, limit: Int, offset: Int): Flow<PagingData<Gif>> {
        return Pager(config = PagingConfig(pageSize = GifSource.ITEMS_PER_PAGE)) {
            GifSource(gifService, fileProcessor,gifDao,query)
        }.flow
    }

    override suspend fun fetchGifsFromLocalStorage(): List<SavedGif> {
        val gifsId = gifDao.getAllGifs().filter {!it.isRemoved }.map { it.id }
        return fileProcessor.readFromFile(gifsId)
    }

    override suspend fun deleteGifFromLocalStorage(fileName:String): Boolean {
        gifDao.updateGif(fileName,true)
        return fileProcessor.deleteFile(fileName)
    }
}