package org.sumit.features.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import org.sumit.features.common.ui.UiState
import org.sumit.features.profile.data.User

class ProfileViewModel : ViewModel() {

    private var _refresh = MutableStateFlow(false)
    private val refresh = _refresh.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val profileUiState: StateFlow<UiState<User>> = combine(refresh) { _ ->
        getUserInfo()
    }.flatMapLatest { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState(isLoading = true)
        )

    fun refresh() {
        _refresh.value = true
    }

    fun getUserInfo(): Flow<UiState<User>> = flow {
        emit(UiState(isLoading = true))

        delay(1000L)

        emit(
            UiState(
                data = User(
                    id = 1L,
                    name = "Sumit",
                    email = "sumitjangir554@gmail.com",
                    myRecipeCount = 10,
                    favoriteRecipeCount = 5,
                    followers = 100
                ),
                isLoggedIn = true,
                isLoading = false
            )
        )
    }
}