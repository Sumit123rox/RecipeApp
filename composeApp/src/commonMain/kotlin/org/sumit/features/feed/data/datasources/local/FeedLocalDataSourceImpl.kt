package org.sumit.features.feed.data.datasources.local

import org.sumit.features.common.data.database.dao.RecipeDao
import org.sumit.features.common.domain.entities.RecipeItem

class FeedLocalDataSourceImpl(private val recipeDao: RecipeDao) : FeedLocalDataSource {
    override suspend fun getRecipes(): List<RecipeItem> = recipeDao.getAllRecipes()
    override suspend fun saveRecipesList(recipes: List<RecipeItem>) =
        recipeDao.insertRecipeBulk(recipes)
}