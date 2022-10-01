package com.natife.myapplication.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.natife.myapplication.fileprocessor.FileProcessor
import com.natife.myapplication.retrofit.GifService
import com.natife.myapplication.room.Gif
import com.natife.myapplication.room.GifDao
import com.natife.myapplication.utils.GifsMapper
import retrofit2.HttpException
import java.io.IOException

class GifSource(
    private val gifService: GifService,
    private val fileProcessor: FileProcessor,
    private val gifDao: GifDao,
    private val query: String
) :
    PagingSource<Int, String>() {
    companion object {
        const val ITEMS_PER_PAGE = 4
        private const val TAG = "GifSource"
    }

    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        return try {
            val nextPage = params.key ?: 1
            val gifDto =
                fetchGifsFromServer(nextPage) ?: return LoadResult.Error(IOException())

            val gifData = GifsMapper.convertGifDtoToGifList(gifDto)
            if (gifData.isEmpty()) {
                return LoadResult.Error(Exception())
            }
            addGifsToLocalStorage(gifData)
            LoadResult.Page(
                data = gifData.map { it.url },
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (gifData.isEmpty()) null else (gifDto.pagination.offset * ITEMS_PER_PAGE) + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    private suspend fun addGifsToLocalStorage(data: List<Gif>) {
        data.forEach { gif ->
            fileProcessor.writeToFile(gif.id + ".gif", gif.url)
        }
        gifDao.insertGifs(data)
    }

    private suspend fun fetchGifsFromServer(nextPage: Int) =
        gifService.getGifsResponse(query = query, limit = ITEMS_PER_PAGE, offset = nextPage)
            .also {
                Log.d(
                    TAG,
                    "fetchGifs: ${it.raw().request.url} offset:${it.body()?.pagination?.offset}"
                )
            }.body()

}