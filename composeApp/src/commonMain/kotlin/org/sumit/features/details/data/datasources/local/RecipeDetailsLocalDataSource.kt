package org.sumit.features.details.data.datasources.local

import org.sumit.features.common.domain.entities.RecipeItem

interface RecipeDetailsLocalDataSource {
    suspend fun getRecipeDetails(recipeId: Long): RecipeItem?
    suspend fun saveRecipe(recipeItem: RecipeItem)
    suspend fun addFavorite(recipeId: Long)
    suspend fun removeFavorite(recipeId: Long)
    suspend fun isFavorite(recipeId: Long): Boolean
}