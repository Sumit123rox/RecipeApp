package org.sumit.di

import org.koin.dsl.module
import org.sumit.dbFactory.DatabaseFactory
import org.sumit.preferences.MultiplatformSettingsFactory

val jvmModule = module {
    single { DatabaseFactory() }
    single { MultiplatformSettingsFactory() }
}

fun initKoinJvm() = initKoin(additionalModules = listOf(jvmModule))
