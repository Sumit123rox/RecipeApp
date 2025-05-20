package org.sumit.features.details.data.datasources.remote

import org.sumit.features.common.domain.entities.RecipeItem

interface RecipeDetailsRemoteDataSource {
    suspend fun getRecipeDetails(recipeId: Long): RecipeItem?
}