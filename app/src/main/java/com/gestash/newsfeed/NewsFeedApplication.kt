package com.gestash.newsfeed

import android.app.Application
import com.gestash.newsfeed.di.Di
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NewsFeedApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@NewsFeedApplication)
            modules(Di.koinModules)
        }
    }
}