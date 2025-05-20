package org.sumit.features.favorites.data.local

import org.sumit.features.common.domain.entities.RecipeItem

interface FavoriteRecipeLocalSource {
    suspend fun getAllFavoriteRecipes(): List<RecipeItem>
    suspend fun addFavorites(recipeId: Long)
    suspend fun removeFavorites(recipeId: Long)
}