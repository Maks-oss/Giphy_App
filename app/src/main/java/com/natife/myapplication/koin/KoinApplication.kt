package com.natife.myapplication.koin

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class KoinApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KoinApplication)
            modules(listOf(
                retrofitModule,
                fileProcessorModule,
                repositoryModule,
                viewModelModule
            ))
        }

    }
}