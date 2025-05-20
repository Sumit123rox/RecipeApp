package org.sumit.features.feed.data.datasources.remote

import org.sumit.features.common.domain.entities.RecipeItem

interface FeedRemoteDataSources {
    suspend fun getRecipes(): List<RecipeItem>
}