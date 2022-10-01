package com.natife.myapplication.koin

import android.content.Context
import androidx.room.Room
import com.natife.myapplication.fileprocessor.FileProcessor
import com.natife.myapplication.fileprocessor.FileProcessorImpl
import com.natife.myapplication.repository.GifRepository
import com.natife.myapplication.repository.GifRepositoryImpl
import com.natife.myapplication.retrofit.RetrofitClient
import com.natife.myapplication.room.GifDatabase
import com.natife.myapplication.ui.screens.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
val databaseModule = module {

    single {
        provideGifDatabase(androidContext())
    }
    single {
        provideDao(get())
    }

}
private fun provideGifDatabase(context: Context) = Room.databaseBuilder(
    context,
    GifDatabase::class.java, "GifDatabase"
).fallbackToDestructiveMigration()
    .build()
private fun provideDao(gifDatabase: GifDatabase) = gifDatabase
//    .also { CoroutineScope(Dispatchers.IO).launch {  it.clearAllTables()} }
    .gifDao()

val repositoryModule = module {
    single<GifRepository> { GifRepositoryImpl(get(), get(),get()) }
}
val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}