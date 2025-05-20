package org.sumit.features.feed.data.datasources.local

import org.sumit.features.common.domain.entities.RecipeItem

interface FeedLocalDataSource {
    suspend fun getRecipes(): List<RecipeItem>
    suspend fun saveRecipesList(recipes: List<RecipeItem>)
}