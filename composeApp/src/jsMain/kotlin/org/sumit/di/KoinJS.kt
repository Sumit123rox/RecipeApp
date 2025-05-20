package org.sumit.di

import org.koin.dsl.module
import org.sumit.dbFactory.DatabaseFactory
import org.sumit.preferences.MultiplatformSettingsFactory

val jsModule = module {
    single { DatabaseFactory() }
    single { MultiplatformSettingsFactory() }
}

fun initKoinJs() = initKoin(additionalModules = listOf(jsModule))
