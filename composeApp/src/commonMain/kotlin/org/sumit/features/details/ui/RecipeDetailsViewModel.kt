package org.sumit.features.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sumit.features.common.domain.entities.RecipeItem
import org.sumit.features.common.ui.UiState
import org.sumit.features.details.domain.repositories.RecipeDetailsRepository

class RecipeDetailsViewModel(
    private val recipeDetailsRepository: RecipeDetailsRepository
) : ViewModel() {

    private val _recipeDetailsUiState = MutableStateFlow(UiState<RecipeItem>())
    val recipeDetailsUiState: StateFlow<UiState<RecipeItem>> = _recipeDetailsUiState

    private val _favoriteUpdateUiState = MutableStateFlow(RecipeDetailsUpdateIsFavUiState())
    val favoriteUpdateUiState = _favoriteUpdateUiState.asStateFlow()

    fun getRecipeDetails(id: Long) {
        viewModelScope.launch {
            val recipeResult = recipeDetailsRepository.getRecipeDetails(id)

            if (recipeResult.isSuccess) {
                _recipeDetailsUiState.update {
                    it.copy(
                        data = recipeResult.getOrNull(),
                        isLoading = false,
                    )
                }
            } else {
                _recipeDetailsUiState.update {
                    it.copy(
                        isLoading = false,
                        error = recipeResult.exceptionOrNull()?.message
                    )
                }
            }
        }
    }

    fun updateIsFavorite(recipeId: Long, isAdding: Boolean) {
        viewModelScope.launch {
            try {
                _favoriteUpdateUiState.update {
                    it.copy(isUpdating = true)
                }

                if (isAdding) {
                    recipeDetailsRepository.addFavorite(recipeId)
                } else {
                    recipeDetailsRepository.removeFavorite(recipeId)
                }

                _recipeDetailsUiState.update {
                    it.copy(
                        data = _recipeDetailsUiState.value.data?.copy(isFavorite = isAdding)
                    )
                }

                _favoriteUpdateUiState.update {
                    it.copy(
                        isUpdating = false,
                        isSuccess = true
                    )
                }
            } catch (e: Exception) {
                _favoriteUpdateUiState.update {
                    it.copy(
                        isUpdating = false,
                        error = e.message
                    )
                }
            }
        }
    }
}