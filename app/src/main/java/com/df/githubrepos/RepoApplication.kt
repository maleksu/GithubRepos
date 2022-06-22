package com.df.githubrepos

import android.app.Application
import com.df.githubrepos.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RepoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RepoApplication)
            modules(appModule)
        }
    }
}