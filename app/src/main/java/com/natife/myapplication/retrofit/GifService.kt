package com.natife.myapplication.retrofit

import com.natife.myapplication.data.dto.GifDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GifService {
    @GET("/search")
    suspend fun getGifsResponse(
        @Query("q") query: String,
        @Query("limit") limit: Int = 25,
        @Query("offset") offset: Int = 0,
        @Query("rating") rating: String = "g",
        @Query("lang") language: String = "en",
    ): Response<GifDto>
}