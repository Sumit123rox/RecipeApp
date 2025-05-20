package org.sumit.features.app.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.sumit.features.app.data.Constants.IS_LOGGED_IN
import org.sumit.features.details.navigation.navigateToDetails
import org.sumit.features.search.navigation.navigateToSearch
import org.sumit.features.tabs.navigation.navigateToTabs
import org.sumit.preferences.AppPreferences

@Composable
fun rememberAppState(
    navController: NavHostController,
    scope: CoroutineScope,
    appPreferences: AppPreferences
): AppState {
    return remember(navController, scope) {
        AppState(navController, scope, appPreferences)
    }
}

@Stable
class AppState(
    val navController: NavHostController,
    scope: CoroutineScope,
    private val preferences: AppPreferences
) {
    private var _isLoggedIn = MutableStateFlow(preferences.getBoolean(IS_LOGGED_IN, false))
    val isLoggedIn = _isLoggedIn.asStateFlow()
    fun navigateToTabs() = navController.navigateToTabs()
    fun navigateBack() = navController.navigateUp()
    fun navigateToDetails(id: Long) = navController.navigateToDetails(id)
    fun navigateToSearch() = navController.navigateToSearch()

    fun updateIsLoggedIn(isLoggedIn: Boolean) {
        _isLoggedIn.value = isLoggedIn
        preferences.putBoolean(IS_LOGGED_IN, isLoggedIn)
    }

    fun onLogout() {
        preferences.clear()
        updateIsLoggedIn(false)
    }
}