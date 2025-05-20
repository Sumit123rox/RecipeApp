package org.sumit.features.details.data.repositories

import org.sumit.features.common.domain.entities.RecipeItem
import org.sumit.features.details.data.datasources.local.RecipeDetailsLocalDataSource
import org.sumit.features.details.data.datasources.remote.RecipeDetailsRemoteDataSource
import org.sumit.features.details.domain.repositories.RecipeDetailsRepository

class RecipeDetailsRepositoryImpl(
    private val recipeDetailsRemoteDataSource: RecipeDetailsRemoteDataSource,
    private val recipeDetailsLocalDataSource: RecipeDetailsLocalDataSource
) : RecipeDetailsRepository {
    override suspend fun getRecipeDetails(id: Long): Result<RecipeItem?> {
        return try {
            val recipeCache = recipeDetailsLocalDataSource.getRecipeDetails(id)

            if (recipeCache != null) {
                val isFavorite = recipeDetailsLocalDataSource.isFavorite(id)
                Result.success(recipeCache.copy(isFavorite = isFavorite))
            } else {
                val recipes = recipeDetailsRemoteDataSource.getRecipeDetails(id)
                    ?: return Result.failure(Exception("Recipe not found"))
                recipeDetailsLocalDataSource.saveRecipe(recipes)
                Result.success(recipes)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addFavorite(recipeId: Long) {
        recipeDetailsLocalDataSource.addFavorite(recipeId)
    }

    override suspend fun removeFavorite(recipeId: Long) {
        recipeDetailsLocalDataSource.removeFavorite(recipeId)
    }
}