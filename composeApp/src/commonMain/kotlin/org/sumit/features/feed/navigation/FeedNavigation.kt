package org.sumit.features.feed.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.sumit.features.app.data.Screen
import org.sumit.features.feed.ui.FeedRoute

fun NavController.navigateToFeed(navOptions: NavOptions? = null) {
    navigate(Screen.Home.route)
}

fun NavGraphBuilder.feedNavGraph(
    navigateToDetails: (Long) -> Unit,
    isUserLoggedIn: () -> Boolean,
    openLoginBottomSheet: (() -> Unit) -> Unit,
    navigateToSearch: () -> Unit,
) {
    composable(Screen.Home.route) {
        FeedRoute(navigateToDetails = navigateToDetails, navigateToSearch = navigateToSearch)
    }
}