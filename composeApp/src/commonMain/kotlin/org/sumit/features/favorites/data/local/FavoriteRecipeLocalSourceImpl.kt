package org.sumit.features.favorites.data.local

import org.sumit.features.common.data.database.dao.FavoriteRecipeDao
import org.sumit.features.common.domain.entities.RecipeItem

class FavoriteRecipeLocalSourceImpl(
    private val favoriteRecipeDao: FavoriteRecipeDao
) : FavoriteRecipeLocalSource {
    override suspend fun getAllFavoriteRecipes(): List<RecipeItem> =
        favoriteRecipeDao.getAllFavoriteRecipes()


    override suspend fun addFavorites(recipeId: Long) {
        favoriteRecipeDao.addFavorite(recipeId)
    }

    override suspend fun removeFavorites(recipeId: Long) {
        favoriteRecipeDao.removeFavorite(recipeId)
    }
}