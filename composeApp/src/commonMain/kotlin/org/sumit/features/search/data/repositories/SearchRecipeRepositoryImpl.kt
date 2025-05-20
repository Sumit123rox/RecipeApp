package org.sumit.features.search.data.repositories

import org.sumit.features.common.domain.entities.RecipeItem
import org.sumit.features.search.data.datasources.local.SearchRecipeLocalDataSource
import org.sumit.features.search.domain.repositories.SearchRecipeRepository

class SearchRecipeRepositoryImpl(private val localDataSource: SearchRecipeLocalDataSource) :
    SearchRecipeRepository {
    override suspend fun searchRecipesByText(query: String): Result<List<RecipeItem>> {
        return try {
            val result = localDataSource.searchRecipesByText(query)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}