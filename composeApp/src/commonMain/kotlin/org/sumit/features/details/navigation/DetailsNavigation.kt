package org.sumit.features.details.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.sumit.features.app.data.Screen
import org.sumit.features.details.ui.DetailsRoute

const val RECIPE_ID_ARG = "recipeId"

fun NavController.navigateToDetails(id: Long, navOptions: NavOptions? = null) {
    navigate(Screen.Detail.route.replace("$RECIPE_ID_ARG={$RECIPE_ID_ARG}", "$RECIPE_ID_ARG=$id"))
}

fun NavGraphBuilder.detailsNavGraph(
    onBackClick: () -> Unit,
    isUserLoggedIn: () -> Boolean,
    openLoginBottomSheet: (() -> Unit) -> Unit
) {
    composable(
        Screen.Detail.route,
        arguments = listOf(navArgument(RECIPE_ID_ARG) { type = NavType.LongType })
    ) {
        val recipeId = it.arguments?.getLong(RECIPE_ID_ARG) ?: 0L
        DetailsRoute(
            recipeId, onBackClick,
            isUserLoggedIn = isUserLoggedIn,
            openLoginBottomSheet = openLoginBottomSheet,
        )
    }
}