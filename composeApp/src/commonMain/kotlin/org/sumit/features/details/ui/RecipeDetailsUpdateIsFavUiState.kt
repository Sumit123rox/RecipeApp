package org.sumit.features.details.ui

data class RecipeDetailsUpdateIsFavUiState(
    val isSuccess: Boolean? = false,
    val isUpdating: Boolean = true,
    val error: String? = null
)
