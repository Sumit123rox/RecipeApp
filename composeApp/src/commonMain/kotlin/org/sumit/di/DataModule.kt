package org.sumit.di

import org.koin.dsl.module
import org.sumit.features.details.data.datasources.local.RecipeDetailsLocalDataSource
import org.sumit.features.details.data.datasources.local.RecipeDetailsLocalDataSourceImpl
import org.sumit.features.details.data.datasources.remote.RecipeDetailsRemoteDataSource
import org.sumit.features.details.data.datasources.remote.RecipeDetailsRemoteDataSourceImpl
import org.sumit.features.details.data.repositories.RecipeDetailsRepositoryImpl
import org.sumit.features.details.domain.repositories.RecipeDetailsRepository
import org.sumit.features.favorites.data.domain.FavoriteRecipeRepository
import org.sumit.features.favorites.data.domain.FavoriteRecipeRepositoryImpl
import org.sumit.features.favorites.data.local.FavoriteRecipeLocalSource
import org.sumit.features.favorites.data.local.FavoriteRecipeLocalSourceImpl
import org.sumit.features.feed.data.datasources.local.FeedLocalDataSource
import org.sumit.features.feed.data.datasources.local.FeedLocalDataSourceImpl
import org.sumit.features.feed.data.datasources.remote.FeedRemoteDataSources
import org.sumit.features.feed.data.datasources.remote.FeedRemoteDataSourcesImpl
import org.sumit.features.feed.data.repositories.FeedRepositoryImpl
import org.sumit.features.feed.domain.repositories.FeedRepository
import org.sumit.features.search.data.datasources.local.SearchRecipeLocalDataSource
import org.sumit.features.search.data.datasources.local.SearchRecipeLocalDataSourceImpl
import org.sumit.features.search.data.repositories.SearchRecipeRepositoryImpl
import org.sumit.features.search.domain.repositories.SearchRecipeRepository
import org.sumit.preferences.AppPreferences
import org.sumit.preferences.AppPreferencesImpl

fun dataModule() = module {
    single<FeedRemoteDataSources> { FeedRemoteDataSourcesImpl(get()) }
    single<FeedLocalDataSource> { FeedLocalDataSourceImpl(get()) }

    single<RecipeDetailsLocalDataSource> { RecipeDetailsLocalDataSourceImpl(get(), get()) }
    single<RecipeDetailsRemoteDataSource> { RecipeDetailsRemoteDataSourceImpl(get()) }

    single<FavoriteRecipeLocalSource> { FavoriteRecipeLocalSourceImpl(get()) }

    single<SearchRecipeLocalDataSource> { SearchRecipeLocalDataSourceImpl(get()) }

    single<FeedRepository> { FeedRepositoryImpl(get(), get()) }
    single<RecipeDetailsRepository> { RecipeDetailsRepositoryImpl(get(), get()) }
    single<FavoriteRecipeRepository> { FavoriteRecipeRepositoryImpl(get()) }

    single<AppPreferences> { AppPreferencesImpl(get()) }

    single<SearchRecipeRepository> { SearchRecipeRepositoryImpl(get()) }
}