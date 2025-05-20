package org.sumit.features.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel
import org.sumit.features.common.domain.entities.RecipeItem
import org.sumit.features.common.ui.UiState

@Composable
fun SearchRoute(
    navigateToDetails: (Long) -> Unit,
    onBackPress: () -> Unit,
    searchViewModel: SearchViewModel = koinViewModel()
) {

    val searchUiState by searchViewModel.searchUiState.collectAsState()
    val searchText by searchViewModel.searchText.collectAsState()

    SearchScreen(
        searchText = searchText,
        onSearchQueryChange = searchViewModel::onSearchQueryChange,
        searchUiState = searchUiState,
        navigateToDetails = navigateToDetails,
        onBackPress = onBackPress
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchText: String,
    onSearchQueryChange: (String) -> Unit,
    searchUiState: UiState<List<RecipeItem>>,
    navigateToDetails: (Long) -> Unit,
    onBackPress: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = { Text(text = "Search Recipes") },
                navigationIcon = {
                    IconButton(
                        onClick = onBackPress
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchScreenContent(
                searchText = searchText,
                onSearchQueryChange = onSearchQueryChange,
                searchUiState = searchUiState,
                navigateToDetails = navigateToDetails
            )
        }
    }
}

@Composable
fun SearchScreenContent(
    searchText: String,
    onSearchQueryChange: (String) -> Unit,
    searchUiState: UiState<List<RecipeItem>>,
    navigateToDetails: (Long) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp).padding(bottom = 16.dp)
    ) {
        OutlinedTextField(
            shape = MaterialTheme.shapes.small,
            value = searchText,
            onValueChange = onSearchQueryChange,
            placeholder = { Text(text = "Search Recipes") },
            modifier = Modifier.fillMaxWidth().background(
                color = MaterialTheme.colorScheme.onPrimary
            ),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                cursorColor = MaterialTheme.colorScheme.primaryContainer,
                focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            )
        )

        Spacer(Modifier.height(16.dp))

        when {
            searchUiState.error != null -> {
                Text(
                    text = "No Recipe Items Found",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            searchUiState.success && !searchUiState.data.isNullOrEmpty() -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(searchUiState.data, key = { it.id }) { recipeItem ->
                        SearchRecipeItem(
                            recipe = recipeItem,
                            navigateToDetails = navigateToDetails
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchRecipeItem(recipe: RecipeItem, navigateToDetails: (Long) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
            .clickable { navigateToDetails(recipe.id) },
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage(
            model = recipe.imageUrl,
            contentDescription = null,
            modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp)),
            onError = {
                println("onError: ${it.result.throwable.message}")
            },
            contentScale = ContentScale.Crop,
        )

        Column {
            Text(
                text = recipe.title,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            )

            Row {
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
}
