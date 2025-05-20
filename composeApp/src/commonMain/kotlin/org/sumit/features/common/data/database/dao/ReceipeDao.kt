package org.sumit.features.common.data.database.dao

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import org.sumit.features.common.data.database.DbHelper
import org.sumit.features.common.data.database.recipeEntityMapper
import org.sumit.features.common.domain.entities.RecipeItem

class RecipeDao(
    private val dbHelper: DbHelper
) {
    suspend fun insertRecipe(recipe: RecipeItem) {
        dbHelper.withDatabase { db ->
            db.recipeEntityQueries.insertRecipe(
                id = recipe.id,
                title = recipe.title,
                description = recipe.description,
                category = recipe.category,
                area = recipe.area,
                imageUrl = recipe.imageUrl,
                youtubeLink = recipe.youtubeLink,
                ingredients = recipe.ingredients,
                instructions = recipe.instructions,
                isFavorite = if (recipe.isFavorite) 1 else 0,
                rating = recipe.rating,
                duration = recipe.duration,
                difficulty = recipe.difficulty
            )
        }
    }

    suspend fun insertRecipeBulk(recipes: List<RecipeItem>) {
        dbHelper.withDatabase { db ->
            recipes.forEach { recipe ->
                db.recipeEntityQueries.insertRecipe(
                    id = recipe.id,
                    title = recipe.title,
                    description = recipe.description,
                    category = recipe.category,
                    area = recipe.area,
                    imageUrl = recipe.imageUrl,
                    youtubeLink = recipe.youtubeLink,
                    ingredients = recipe.ingredients,
                    instructions = recipe.instructions,
                    isFavorite = if (recipe.isFavorite) 1 else 0,
                    rating = recipe.rating,
                    duration = recipe.duration,
                    difficulty = recipe.difficulty
                )
            }
        }
    }

    suspend fun upsertRecipeBulk(recipes: List<RecipeItem>) {
        dbHelper.withDatabase { db ->
            recipes.forEach { recipe ->
                db.recipeEntityQueries.upsertRecipe(
                    id = recipe.id,
                    title = recipe.title,
                    description = recipe.description,
                    category = recipe.category,
                    area = recipe.area,
                    imageUrl = recipe.imageUrl,
                    youtubeLink = recipe.youtubeLink,
                    ingredients = recipe.ingredients,
                    instructions = recipe.instructions,
                    isFavorite = if (recipe.isFavorite) 1 else 0,
                    rating = recipe.rating,
                    duration = recipe.duration,
                    difficulty = recipe.difficulty
                )
            }
        }
    }

    suspend fun getAllRecipes(): List<RecipeItem> {
        return dbHelper.withDatabase { db ->
            db.recipeEntityQueries.selectAllRecipes().awaitAsList().map { recipe ->
                recipeEntityMapper(recipe)
            }
        }
    }

    suspend fun getRecipes(id: Long): RecipeItem? {
        return dbHelper.withDatabase { db ->
            db.recipeEntityQueries.selectRecipeById(id).awaitAsOneOrNull()?.let {
                recipeEntityMapper(it)
            }
        }
    }

    suspend fun deleteByRecipeId(id: Long) {
        return dbHelper.withDatabase { db ->
            db.recipeEntityQueries.detelteRecipeById(id)
        }
    }

    suspend fun searchRecipeByText(query: String): List<RecipeItem> {
        return dbHelper.withDatabase { db ->
            db.recipeEntityQueries.searchRecipeByText(query).awaitAsList().map { recipe ->
                recipeEntityMapper(recipe)
            }
        }
    }

}