package com.natife.myapplication.koin

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.BuildConfig
import org.koin.core.logger.Level

class KoinApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@KoinApplication)
            modules(listOf(
                retrofitModule,
                fileProcessorModule,
                databaseModule,
                repositoryModule,
                viewModelModule
            ))
        }

    }
}