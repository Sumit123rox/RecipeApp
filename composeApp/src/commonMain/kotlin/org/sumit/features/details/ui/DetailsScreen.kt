package org.sumit.features.details.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel
import org.sumit.features.common.data.models.capitalizeFirstWord
import org.sumit.features.common.domain.entities.RecipeItem
import org.sumit.features.common.ui.UiState
import org.sumit.features.common.ui.components.ErrorContent
import org.sumit.features.common.ui.components.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsRoute(
    recipeId: Long,
    onBackClick: () -> Unit,
    detailsViewModel: RecipeDetailsViewModel = koinViewModel(),
    isUserLoggedIn: () -> Boolean,
    openLoginBottomSheet: (() -> Unit) -> Unit
) {
    LaunchedEffect(Unit) {
        detailsViewModel.getRecipeDetails(recipeId)
    }

    val detailsUiState by detailsViewModel.recipeDetailsUiState.collectAsStateWithLifecycle()
    val favoriteUpdateUiState by detailsViewModel.favoriteUpdateUiState.collectAsStateWithLifecycle()

    var showAlertDialog by remember {
        mutableStateOf(false)
    }

    val onSaveClick: (RecipeItem) -> Unit = {
        if (!isUserLoggedIn()) {
            showAlertDialog = true
        } else {
            detailsViewModel.updateIsFavorite(recipeId = it.id, isAdding = !it.isFavorite)
        }
    }

    val uriHandler = LocalUriHandler.current

    if (showAlertDialog) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = {
                showAlertDialog = false
            },
            title = {
                Text(
                    text = "Update Favorites",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            text = {
                Text(
                    text = "Login to Add/Remove Favorites"
                )
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                    ),
                    onClick = {
                        showAlertDialog = false
                        openLoginBottomSheet {
                            detailsUiState.data?.let {
                                detailsViewModel.updateIsFavorite(
                                    recipeId = it.id,
                                    isAdding = !it.isFavorite
                                )
                            }
                        }
                    }
                ) {
                    Text(text = "Login")
                }
            },
            dismissButton = {
                OutlinedButton(
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primaryContainer
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    ),
                    onClick = {
                        showAlertDialog = false
                    }
                ) {
                    Text(text = "Cancel", color = MaterialTheme.colorScheme.primaryContainer)
                }
            }
        )
    }

    DetailsScreen(
        detailsUiState = detailsUiState,
        favoriteUpdateUiState = favoriteUpdateUiState,
        onBackClick = onBackClick,
        onWatchVideoClick = {
            if (it.isNotEmpty())
                uriHandler.openUri(it)
        },
        onSaveClick = onSaveClick
    )
}

@Composable
fun DetailsScreen(
    detailsUiState: UiState<RecipeItem>,
    favoriteUpdateUiState: RecipeDetailsUpdateIsFavUiState,
    onBackClick: () -> Unit,
    onWatchVideoClick: (String) -> Unit,
    onSaveClick: (RecipeItem) -> Unit
) {
    Scaffold(modifier = Modifier) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            when {
                detailsUiState.isLoading -> {
                    LoadingScreen()
                }

                detailsUiState.error != null -> {
                    ErrorContent(showButton = true) {
                        onBackClick()
                    }
                }

                detailsUiState.data != null -> {
                    RecipeDetailsContent(
                        recipe = detailsUiState.data,
                        onBackClick = onBackClick,
                        onWatchVideoClick = onWatchVideoClick,
                        onSaveClick = onSaveClick
                    )
                }
            }
        }
    }
}

@Composable
fun RecipeDetailsContent(
    recipe: RecipeItem,
    onBackClick: () -> Unit,
    onWatchVideoClick: (String) -> Unit,
    onSaveClick: (RecipeItem) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        RecipeMainContent(recipe = recipe, onWatchVideoClick = onWatchVideoClick)

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(WindowInsets.statusBars.asPaddingValues())
                .padding(vertical = 32.dp, horizontal = 16.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(30.dp)
                    .background(
                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            IconButton(
                onClick = { onSaveClick(recipe) },
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(30.dp)
                    .background(
                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = if (recipe.isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
fun RecipeMainContent(recipe: RecipeItem, onWatchVideoClick: (String) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                .padding(bottom = 12.dp)
        ) {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(300.dp)
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            )

            RecipeDetails(recipe = recipe)

            Column(
                modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = recipe.description,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            IngredientsList(
                ingredients = recipe.ingredients.map {
                    val item = it.split(":")
                    if (item.isNotEmpty() && item.size == 2) {
                        Pair(item[0].trim().capitalizeFirstWord(), item[1].trim())
                    } else {
                        Pair("", "")
                    }
                }.filter {
                    it.first.isNotEmpty() && it.second.isNotEmpty()
                }.filterNot {
                    it.first.contains("null") || it.second.contains("null")
                }
            )

            Spacer(Modifier.height(16.dp))

            Instructions(instructions = recipe.instructions)

            WatchVideoButton(recipe.youtubeLink, onWatchVideoClick = onWatchVideoClick)
        }
    }
}

@Composable
fun RecipeDetails(recipe: RecipeItem) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = recipe.title, style = MaterialTheme.typography.titleLarge)
        Row(
            modifier = Modifier.padding(top = 16.dp),
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

            Spacer(Modifier.width(16.dp))

            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = "${recipe.rating} star",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )

            Spacer(Modifier.width(16.dp))

            Text(
                text = recipe.difficulty,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
fun IngredientsList(ingredients: List<Pair<String, String>>) {
    if (ingredients.isNotEmpty()) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            ingredients.forEach {
                IngredientsItem(name = it.first, quantity = it.second)
            }
        }
    }
}

@Composable
fun IngredientsItem(name: String, quantity: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            text = quantity,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun Instructions(instructions: List<String>) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Instructions",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        instructions.forEachIndexed { index, value ->
            Text(
                text = "${index + 1}. $value",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
fun WatchVideoButton(
    youtubeLinkUrl: String,
    onWatchVideoClick: (String) -> Unit
) {
    Button(
        onClick = { onWatchVideoClick(youtubeLinkUrl) }, colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        modifier = Modifier.padding(16.dp).fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Watch video",
            tint = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(Modifier.width(16.dp))

        Text(
            text = "Watch Video",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
        )
    }
}















