package org.sumit.features.search.data.datasources.local

import org.sumit.features.common.data.database.dao.RecipeDao
import org.sumit.features.common.domain.entities.RecipeItem

class SearchRecipeLocalDataSourceImpl(
    private val recipeDao: RecipeDao
) : SearchRecipeLocalDataSource {
    override suspend fun searchRecipesByText(query: String): List<RecipeItem> {
        return recipeDao.searchRecipeByText(query)
    }
}