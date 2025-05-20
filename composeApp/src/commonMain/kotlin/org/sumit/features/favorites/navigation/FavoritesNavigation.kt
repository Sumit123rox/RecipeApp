package org.sumit.features.favorites.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.sumit.features.app.data.Screen
import org.sumit.features.favorites.ui.FavoritesRoute

fun NavController.navigateToFavorites(navOptions: NavOptions) {
    navigate(Screen.Favorites.route)
}

fun NavGraphBuilder.favoritesNavGraph(
    navigateToDetails: (Long) -> Unit,
    isUserLoggedIn: () -> Boolean,
) {
    composable(Screen.Favorites.route) {
        FavoritesRoute(navigateToDetails = navigateToDetails, isUserLoggedIn = isUserLoggedIn)
    }
}