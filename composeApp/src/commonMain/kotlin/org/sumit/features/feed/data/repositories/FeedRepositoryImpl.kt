package org.sumit.features.feed.data.repositories

import org.sumit.features.common.domain.entities.RecipeItem
import org.sumit.features.feed.data.datasources.local.FeedLocalDataSource
import org.sumit.features.feed.data.datasources.remote.FeedRemoteDataSources
import org.sumit.features.feed.domain.repositories.FeedRepository

class FeedRepositoryImpl(
    private val feedRemoteDataSources: FeedRemoteDataSources,
    private val feedLocalDataSource: FeedLocalDataSource
) : FeedRepository {
    override suspend fun getRecipesList(): Result<List<RecipeItem>> {
        return try {
            val recipesListCache = feedLocalDataSource.getRecipes()
            val count = recipesListCache.count()

            if (count > 0) {
                Result.success(recipesListCache)
            } else {
                val recipes = feedRemoteDataSources.getRecipes()
                feedLocalDataSource.saveRecipesList(recipes)
                Result.success(recipes)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}