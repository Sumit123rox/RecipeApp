package org.sumit.features.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.sumit.features.app.data.Screen
import org.sumit.features.search.ui.SearchRoute

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    navigate(Screen.Search.route)
}

fun NavGraphBuilder.searchNavGraph(
    navigateToDetails: (Long) -> Unit,
    onBackPress: () -> Unit
) {
    composable(Screen.Search.route) {
        SearchRoute(navigateToDetails = navigateToDetails, onBackPress = onBackPress)
    }
}