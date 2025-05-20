package org.sumit.features.details.domain.repositories

import org.sumit.features.common.domain.entities.RecipeItem

interface RecipeDetailsRepository {
    suspend fun getRecipeDetails(id: Long): Result<RecipeItem?>
    suspend fun addFavorite(recipeId: Long)
    suspend fun removeFavorite(recipeId: Long)
}