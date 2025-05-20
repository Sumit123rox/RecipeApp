package org.sumit.features.favorites.data.domain

import org.sumit.features.common.domain.entities.RecipeItem

interface FavoriteRecipeRepository {
    suspend fun getAllFavoriteRecipes(): Result<List<RecipeItem>>
    suspend fun addFavorites(recipeId: Long)
    suspend fun removeFavorites(recipeId: Long)
}