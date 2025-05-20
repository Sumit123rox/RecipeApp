package org.sumit.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import org.sumit.features.common.data.database.DbHelper
import org.sumit.features.common.data.database.dao.FavoriteRecipeDao
import org.sumit.features.common.data.database.dao.RecipeDao
import kotlin.coroutines.CoroutineContext

fun cacheModule() = module {
    single<CoroutineContext> { Dispatchers.Default }
    single { CoroutineScope(get()) }

    single { DbHelper(get()) }
    single { RecipeDao(get()) }
    single { FavoriteRecipeDao(get()) }
}