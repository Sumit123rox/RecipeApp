package org.sumit.features.feed.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sumit.features.common.domain.entities.RecipeItem
import org.sumit.features.feed.domain.repositories.FeedRepository
import org.sumit.features.common.ui.UiState

class FeedViewModel(
    private val feedRepository: FeedRepository,
) : ViewModel() {

    private var _feedUiState = MutableStateFlow(UiState<List<RecipeItem>>())
    var feedUiState: StateFlow<UiState<List<RecipeItem>>> = _feedUiState

    init {
        viewModelScope.launch {
            getRecipesList()
        }
    }

    private suspend fun getRecipesList() {
        val recipes = feedRepository.getRecipesList()
        if (recipes.isSuccess) {
            _feedUiState.update {
                it.copy(
                    data = recipes.getOrDefault(emptyList()),
                    isLoading = false,
                    error = null
                )
            }
        } else {
            _feedUiState.update {
                it.copy(
                    data = emptyList(),
                    isLoading = false,
                    error = recipes.exceptionOrNull()?.message
                )
            }
        }
    }
}