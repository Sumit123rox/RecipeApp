package org.sumit.features.app.data

import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.sumit.features.details.navigation.RECIPE_ID_ARG
import recipeapp.composeapp.generated.resources.Res
import recipeapp.composeapp.generated.resources.bookmark_selected
import recipeapp.composeapp.generated.resources.bookmark_unselected
import recipeapp.composeapp.generated.resources.detail
import recipeapp.composeapp.generated.resources.favorites
import recipeapp.composeapp.generated.resources.home
import recipeapp.composeapp.generated.resources.home_selected
import recipeapp.composeapp.generated.resources.home_unselected
import recipeapp.composeapp.generated.resources.profile
import recipeapp.composeapp.generated.resources.profile_selected
import recipeapp.composeapp.generated.resources.profile_unselected
import recipeapp.composeapp.generated.resources.search
import recipeapp.composeapp.generated.resources.tabs

sealed class Screen(
    val route: String,
    val resourceId: StringResource,
    val selectedIcon: DrawableResource? = null,
    val unselectedIcon: DrawableResource? = null
) {
    data object Search : Screen(
        "search",
        Res.string.search
    )

    data object Detail : Screen(
        "detail?$RECIPE_ID_ARG={$RECIPE_ID_ARG}",
        Res.string.detail
    )

    data object Tabs : Screen(
        "tabs",
        Res.string.tabs
    )

    data object Home : Screen(
        "home",
        Res.string.home,
        selectedIcon = Res.drawable.home_selected,
        unselectedIcon = Res.drawable.home_unselected
    )

    data object Favorites : Screen(
        "favorites",
        Res.string.favorites,
        selectedIcon = Res.drawable.bookmark_selected,
        unselectedIcon = Res.drawable.bookmark_unselected
    )

    data object Profile : Screen(
        "profile",
        Res.string.profile,
        selectedIcon = Res.drawable.profile_selected,
        unselectedIcon = Res.drawable.profile_unselected
    )
}
