package org.sumit.features.common.data.database.dao

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.sumit.features.common.data.database.DbHelper
import org.sumit.features.common.data.database.recipeEntityMapper
import org.sumit.features.common.domain.entities.RecipeItem

class FavoriteRecipeDao(
    private val dbHelper: DbHelper
) {

    suspend fun addFavorite(recipeId: Long) {
        val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        dbHelper.withDatabase { db ->
            db.favoriteRecipeEntityQueries.upsertFavorite(
                recipe_id = recipeId, added_at = currentDateTime.toString()
            )
        }
    }

    suspend fun removeFavorite(recipeId: Long) {
        dbHelper.withDatabase { db ->
            db.favoriteRecipeEntityQueries.deteteFavoriteByRecipeById(
                recipe_id = recipeId
            )
        }
    }

    suspend fun getAllFavoriteRecipes(): List<RecipeItem> {
        return dbHelper.withDatabase { db ->
            db.favoriteRecipeEntityQueries.selectAllFavoritesRecipes().awaitAsList().map {
                recipeEntityMapper(it)
            }
        }
    }

    suspend fun isFavorite(recipeId: Long): Boolean {
        return dbHelper.withDatabase { db ->
            db.favoriteRecipeEntityQueries.selectFavoriteByRecipeId(recipeId)
                .awaitAsOneOrNull() != null
        }
    }
}