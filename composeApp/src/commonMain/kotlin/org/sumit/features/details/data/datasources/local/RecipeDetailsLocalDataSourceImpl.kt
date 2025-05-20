package org.sumit.features.details.data.datasources.local

import org.sumit.features.common.data.database.dao.FavoriteRecipeDao
import org.sumit.features.common.data.database.dao.RecipeDao
import org.sumit.features.common.domain.entities.RecipeItem

class RecipeDetailsLocalDataSourceImpl(
    private val recipeDao: RecipeDao,
    private val favoriteRecipeDao: FavoriteRecipeDao
) : RecipeDetailsLocalDataSource {
    override suspend fun getRecipeDetails(recipeId: Long): RecipeItem? {
        return recipeDao.getRecipes(recipeId)
    }

    override suspend fun saveRecipe(recipeItem: RecipeItem) {
        recipeDao.insertRecipe(recipeItem)
    }

    override suspend fun addFavorite(recipeId: Long) {
        favoriteRecipeDao.addFavorite(recipeId)
    }

    override suspend fun removeFavorite(recipeId: Long) {
        favoriteRecipeDao.removeFavorite(recipeId)
    }

    override suspend fun isFavorite(recipeId: Long): Boolean =
        favoriteRecipeDao.isFavorite(recipeId)
}