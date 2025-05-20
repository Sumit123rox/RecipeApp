package org.sumit.features.favorites.data.domain

import org.sumit.features.common.domain.entities.RecipeItem
import org.sumit.features.favorites.data.local.FavoriteRecipeLocalSource

class FavoriteRecipeRepositoryImpl(
    private val favoriteRecipeLocalSource: FavoriteRecipeLocalSource
) : FavoriteRecipeRepository {
    override suspend fun getAllFavoriteRecipes(): Result<List<RecipeItem>> {
        return try {
            val favoriteRecipes = favoriteRecipeLocalSource.getAllFavoriteRecipes()

            if (favoriteRecipes.isNotEmpty()) {
                Result.success(favoriteRecipes)
            } else {
                Result.failure(Exception("No favorite recipes found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addFavorites(recipeId: Long) {
        favoriteRecipeLocalSource.addFavorites(recipeId = recipeId)
    }

    override suspend fun removeFavorites(recipeId: Long) {
        favoriteRecipeLocalSource.removeFavorites(recipeId = recipeId)
    }
}