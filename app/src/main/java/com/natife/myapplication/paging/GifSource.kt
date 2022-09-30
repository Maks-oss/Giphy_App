package com.natife.myapplication.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.natife.myapplication.retrofit.GifService
import com.natife.myapplication.utils.GifsMapper
import retrofit2.HttpException
import java.io.IOException

class GifSource(private val gifService: GifService, private val query: String) :
    PagingSource<Int, String>() {
    companion object {
        const val ITEMS_PER_PAGE = 4
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
                gifService.getGifsResponse(query = query, limit = ITEMS_PER_PAGE, offset = nextPage)
                    .also {
                        Log.d("TAG", "fetchGifs: ${it.raw().request.url} ${it.body()}")
                    }.body() ?: return LoadResult.Error(IOException())
            val data = GifsMapper.convertGifDtoToGifList(gifDto)
            LoadResult.Page(
                data = data,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (data.isEmpty()) null else (gifDto.pagination.offset * ITEMS_PER_PAGE) + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}