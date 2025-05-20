package org.sumit.features.common.data.database

import org.sumit.features.common.domain.entities.RecipeItem
import orgsumit.Recipe

fun recipeEntityMapper(recipe: Recipe) = RecipeItem(
    id = recipe.id,
    title = recipe.title,
    description = recipe.description,
    category = recipe.category,
    area = recipe.area,
    imageUrl = recipe.imageUrl,
    youtubeLink = recipe.youtubeLink,
    ingredients = recipe.ingredients,
    instructions = recipe.instructions,
    isFavorite = recipe.isFavorite.toInt() == 1,
    rating = recipe.rating,
    duration = recipe.duration ?: "20 min",
    difficulty = recipe.difficulty ?: "Easy"
)