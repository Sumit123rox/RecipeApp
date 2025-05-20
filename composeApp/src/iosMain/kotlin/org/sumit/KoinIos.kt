package org.sumit

import org.koin.dsl.module
import org.sumit.dbFactory.DatabaseFactory
import org.sumit.di.initKoin
import org.sumit.preferences.MultiplatformSettingsFactory

val iosModule = module {
    single { DatabaseFactory() }
    single { MultiplatformSettingsFactory() }
}

fun initKoinIOS() = initKoin(additionalModules = listOf(iosModule))
