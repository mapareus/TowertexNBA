package com.towertex.nba

import android.app.Application
import com.towertex.nba.di.repositoryModule
import com.towertex.nba.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NBAApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@NBAApplication)
            modules(listOf(repositoryModule, viewModelModule))
        }
    }
}