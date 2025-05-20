package org.sumit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.sumit.features.app.data.rememberAppState
import org.sumit.features.app.navigation.AppNavHost
import org.sumit.features.login.ui.LoginScreenBottomSheet
import org.sumit.features.login.ui.LoginViewModel
import org.sumit.features.theme.RecipeAppTheme

@Composable
@Preview
fun App(
    loginViewModel: LoginViewModel = koinViewModel()
) {
    RecipeAppTheme {
        KoinContext {

            var showLoginBottomSheet by remember { mutableStateOf(false) }
            val appState = rememberAppState(
                rememberNavController(),
                scope = rememberCoroutineScope(),
                appPreferences = koinInject()
            )

            val isLoggedIn by appState.isLoggedIn.collectAsState()
            val isUserLoggedIn: () -> Boolean = {
                isLoggedIn
            }

            var loginCallback: () -> Unit by remember { mutableStateOf({}) }

            val openLoginBottomSheet: (() -> Unit) -> Unit = {
                showLoginBottomSheet = true
                loginCallback = it
            }

            val onLoginSuccess: () -> Unit = {
                showLoginBottomSheet = false
                appState.updateIsLoggedIn(true)
                loginViewModel.resetState()
                loginCallback()
            }

            val onLogout: () -> Unit = {
                appState.onLogout()
                loginViewModel.resetState()
            }

            val onCloseSheet: () -> Unit = {
                showLoginBottomSheet = false
                loginViewModel.resetState()
            }

            LoginScreenBottomSheet(
                loginViewModel = loginViewModel,
                showBottomSheet = showLoginBottomSheet,
                onClose = onCloseSheet,
                onLoginSuccess = onLoginSuccess
            )

            AppNavHost(
                appState = appState,
                isUserLoggedIn = isUserLoggedIn,
                openLoginBottomSheet = openLoginBottomSheet,
                onLogout = onLogout
            )
        }
    }
}