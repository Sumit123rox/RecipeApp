package org.sumit.features.favorites.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sumit.features.common.domain.entities.RecipeItem
import org.sumit.features.common.ui.UiState
import org.sumit.features.details.ui.RecipeDetailsUpdateIsFavUiState
import org.sumit.features.favorites.data.domain.FavoriteRecipeRepository

class FavoritesViewModel(
    private val favoriteRecipeRepository: FavoriteRecipeRepository
) : ViewModel() {

    private val _favoriteRecipes = MutableStateFlow(UiState<List<RecipeItem>>())
    val favoriteRecipes = _favoriteRecipes.asStateFlow()

    init {
        viewModelScope.launch {
            getFavoriteRecipes()
        }
    }

    suspend fun getFavoriteRecipes() {
        val favoriteRecipes = favoriteRecipeRepository.getAllFavoriteRecipes()

        if (favoriteRecipes.isSuccess) {
            _favoriteRecipes.update {
                it.copy(
                    data = favoriteRecipes.getOrDefault(emptyList()),
                    isLoading = false
                )
            }
        } else {
            _favoriteRecipes.update {
                it.copy(
                    error = favoriteRecipes.exceptionOrNull()?.message,
                    isLoading = false
                )
            }
        }
    }

    suspend fun addFavorite(recipeId: Long) {
        favoriteRecipeRepository.addFavorites(recipeId)
    }

    suspend fun removeFavorite(recipeId: Long) {
        favoriteRecipeRepository.removeFavorites(recipeId)
    }
}