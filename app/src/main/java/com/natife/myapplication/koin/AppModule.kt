package com.natife.myapplication.koin

import com.natife.myapplication.fileprocessor.FileProcessor
import com.natife.myapplication.fileprocessor.FileProcessorImpl
import com.natife.myapplication.repository.GifRepository
import com.natife.myapplication.repository.GifRepositoryImpl
import com.natife.myapplication.retrofit.RetrofitClient
import com.natife.myapplication.ui.screens.main.MainViewModel
import okhttp3.internal.platform.android.AndroidSocketAdapter.Companion.factory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val retrofitModule = module {
    factory { RetrofitClient.provideRetrofitAuth() }
    single { RetrofitClient.provideTrackApi(get()) }
}
val fileProcessorModule = module {
    single<FileProcessor> { FileProcessorImpl(androidContext()) }
}

val repositoryModule = module {
    single<GifRepository> { GifRepositoryImpl(get(),get()) }
}
val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}