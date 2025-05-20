package org.sumit

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.module
import org.sumit.dbFactory.DatabaseFactory
import org.sumit.di.initKoin
import org.sumit.preferences.MultiplatformSettingsFactory

class RecipeApp : Application() {

    private val appModule = module {
        single { DatabaseFactory(applicationContext) }
        single { MultiplatformSettingsFactory(applicationContext) }
    }

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@RecipeApp)
            modules(appModule)
        }
    }
}