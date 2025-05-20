package org.sumit.features.common.ui

data class UiState<T>(
    val data: T? = null,
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = true,
    val error: String? = null,
    val idle: Boolean = true,
    val success: Boolean = false
)