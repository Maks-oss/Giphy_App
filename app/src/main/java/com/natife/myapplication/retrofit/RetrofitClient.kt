package com.natife.myapplication.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.giphy.com/v1/gifs/"


    fun provideTrackApi(retrofit: Retrofit): GifService = retrofit.create(
        GifService::class.java
    )


    fun provideRetrofitAuth(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}