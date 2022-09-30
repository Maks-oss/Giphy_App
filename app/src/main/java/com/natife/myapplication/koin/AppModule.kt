package com.natife.myapplication.koin

import com.natife.myapplication.repository.GifRepository
import com.natife.myapplication.repository.GifRepositoryImpl
import com.natife.myapplication.retrofit.RetrofitClient
import com.natife.myapplication.ui.screens.main.MainViewModel
import okhttp3.internal.platform.android.AndroidSocketAdapter.Companion.factory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val retrofitModule = module {
    factory { RetrofitClient.provideRetrofitAuth() }
    single { RetrofitClient.provideTrackApi(get()) }
}
//val databaseModule = module {
//    fun provideDataBase(application: Application): AppDatabase {
//        return Room.databaseBuilder(application, AppDatabase::class.java, "MusicDB")
//            .fallbackToDestructiveMigration()
//            .build()
//    }
//
//    fun provideDao(dataBase: AppDatabase): FeedDao {
//        return dataBase.feedDao()
//    }
//    single { provideDataBase(androidApplication()) }
//    single { provideDao(get()) }
//}

val repositoryModule = module {
    single<GifRepository> { GifRepositoryImpl(get()) }
}
val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}