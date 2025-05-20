package org.sumit.features.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sumit.features.common.domain.entities.RecipeItem
import org.sumit.features.common.ui.UiState
import org.sumit.features.search.domain.repositories.SearchRecipeRepository

class SearchViewModel(private val searchRecipeRepository: SearchRecipeRepository) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _searchUiState = MutableStateFlow(UiState<List<RecipeItem>>())
    val searchUiState = _searchUiState.asStateFlow()

    init {
        triggerFetchItems()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun triggerFetchItems() = viewModelScope.launch {
        _searchText
            .debounce(timeoutMillis = 500L)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                flow {
                    val result =
                        if (query.isNotEmpty()) searchRecipeRepository.searchRecipesByText(query)
                            .getOrNull() else emptyList()
                    emit(result)
                }
            }
            .catch { error ->
                _searchUiState.update {
                    it.copy(
                        idle = false,
                        error = error.message
                    )
                }
            }
            .collect { results ->
                _searchUiState.update {
                    it.copy(
                        idle = false,
                        success = true,
                        data = results
                    )
                }
            }
    }

    fun onSearchQueryChange(query: String) {
        _searchText.value = query
    }

}