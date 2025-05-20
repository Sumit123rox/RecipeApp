package org.sumit.features.feed.domain.repositories

import org.sumit.features.common.domain.entities.RecipeItem

interface FeedRepository {
    suspend fun getRecipesList(): Result<List<RecipeItem>>
}