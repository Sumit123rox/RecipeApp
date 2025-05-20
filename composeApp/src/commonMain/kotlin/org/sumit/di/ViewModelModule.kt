package org.sumit.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.sumit.features.details.ui.RecipeDetailsViewModel
import org.sumit.features.favorites.ui.FavoritesViewModel
import org.sumit.features.feed.ui.FeedViewModel
import org.sumit.features.login.ui.LoginViewModel
import org.sumit.features.profile.ui.ProfileViewModel
import org.sumit.features.search.ui.SearchViewModel

fun viewModelModule() = module {
    viewModel {
        FeedViewModel(get())
    }

    viewModel {
        RecipeDetailsViewModel(get())
    }

    viewModel {
        FavoritesViewModel(get())
    }

    viewModel { ProfileViewModel() }

    viewModel { LoginViewModel() }

    viewModel { SearchViewModel(get()) }

}