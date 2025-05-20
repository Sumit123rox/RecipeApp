package org.sumit.features.feed.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel
import org.sumit.features.common.domain.entities.RecipeItem
import org.sumit.features.common.ui.UiState
import org.sumit.features.common.ui.components.ErrorContent
import org.sumit.features.common.ui.components.LoadingScreen

@Composable
fun FeedRoute(
    navigateToDetails: (Long) -> Unit,
    navigateToSearch: () -> Unit,
    feedViewModel: FeedViewModel = koinViewModel()
) {

    val feedUiState by feedViewModel.feedUiState.collectAsStateWithLifecycle()

    FeedScreen(
        navigateToDetails = navigateToDetails,
        feedUiState = feedUiState,
        navigateToSearch = navigateToSearch
    )
}

@Composable
fun FeedScreen(
    navigateToDetails: (Long) -> Unit,
    feedUiState: UiState<List<RecipeItem>>,
    navigateToSearch: () -> Unit,
) {
    val recipes = feedUiState.data
    Scaffold(
        topBar = { TopBar(navigateToSearch = navigateToSearch) },
    ) { innerPadding ->
        when {
            feedUiState.isLoading -> {
                LoadingScreen()
            }

            feedUiState.error != null -> {
                ErrorContent()
            }

            recipes != null -> {
                FeedContent(
                    padding = innerPadding,
                    recipes = recipes,
                    navigateToDetails = navigateToDetails,
                    navigateToSearch = navigateToSearch
                )
            }
        }
    }
}

@Composable
private fun TopBar(
    navigateToSearch: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "Hi Sumit!",
            color = MaterialTheme.colorScheme.primaryContainer,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Got a tasty dish in mind?",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )

        SearchBar(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp).height(45.dp)
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(12.dp)
                ).border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(12.dp)
                ).clickable { navigateToSearch() },
        )
    }
}

@Composable
private fun SearchBar(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.CenterStart) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Search Any Recipes",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun FeedContent(
    padding: PaddingValues,
    recipes: List<RecipeItem>,
    navigateToDetails: (Long) -> Unit,
    navigateToSearch: () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(top = padding.calculateTopPadding()),
        contentPadding = PaddingValues(bottom = 12.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            TopRecipesList(
                title = "Top Recommendation",
                recipes = recipes.reversed(),
                navigateToDetails = navigateToDetails
            )
        }

        recipesOfTheWeek(
            title = "Reciep of the Week",
            recipes = recipes,
            navigateToDetails = navigateToDetails
        )
    }
}

@Composable
private fun TopRecipesList(
    title: String,
    recipes: List<RecipeItem>,
    navigateToDetails: (Long) -> Unit,
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            items(recipes, key = { it.id }) { recipe ->
                RecipeCard(
                    modifier = Modifier.width(110.dp),
                    imageModifier = Modifier.width(120.dp).height(140.dp).clip(
                        RoundedCornerShape(12.dp)
                    ).clickable { navigateToDetails(recipe.id) },
                    recipe = recipe
                )
            }
        }
    }
}

private fun LazyGridScope.recipesOfTheWeek(
    title: String,
    recipes: List<RecipeItem>,
    navigateToDetails: (Long) -> Unit,
) {
    item(span = { GridItemSpan(maxLineSpan) }) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )
    }

    itemsIndexed(recipes, key = { _, it -> it.id }) { index, recipe ->

        val cardPaddingStart = if (index % 2 == 0) 16.dp else 0.dp
        val cardPaddingEnd = if (index % 2 == 0) 0.dp else 16.dp

        RecipeCard(
            modifier = Modifier.width(110.dp)
                .padding(start = cardPaddingStart, end = cardPaddingEnd),
            imageModifier = Modifier.fillMaxWidth().height(130.dp).clip(
                RoundedCornerShape(12.dp)
            ).clickable { navigateToDetails(recipe.id) },
            recipe = recipe
        )
    }
}

@Composable
fun RecipeCard(modifier: Modifier = Modifier, imageModifier: Modifier, recipe: RecipeItem) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = recipe.imageUrl,
            contentDescription = null,
            modifier = imageModifier,
            onError = {
                println("onError: ${it.result.throwable.message}")
            },
            contentScale = ContentScale.Crop,
        )
        Text(
            text = recipe.title,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.padding(end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text = recipe.duration,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text = "${recipe.rating}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}
