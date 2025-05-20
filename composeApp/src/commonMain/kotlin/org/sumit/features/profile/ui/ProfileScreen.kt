package org.sumit.features.profile.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.sumit.features.common.ui.UiState
import org.sumit.features.common.ui.components.ErrorContent
import org.sumit.features.common.ui.components.LoadingScreen
import org.sumit.features.profile.data.User
import recipeapp.composeapp.generated.resources.Res
import recipeapp.composeapp.generated.resources.avatar
import recipeapp.composeapp.generated.resources.profile_dummy

@Composable
fun ProfileRoute(
    profileViewModel: ProfileViewModel = koinViewModel(),
    isUserLoggedIn: () -> Boolean,
    openLoginBottomSheet: (() -> Unit) -> Unit,
    onLogout: () -> Unit
) {
    val profileUiState by profileViewModel.profileUiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        profileViewModel.getUserInfo()
    }

    ProfileScreen(
        profileUiState,
        isUserLoggedIn = isUserLoggedIn,
        onEditProfile = { },
        onLogin = {
            openLoginBottomSheet {
                profileViewModel.refresh()
            }
        },
        onLogout = onLogout
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileUiState: UiState<User>,
    isUserLoggedIn: () -> Boolean,
    onEditProfile: () -> Unit,
    onLogin: () -> Unit,
    onLogout: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = { Text(text = "Profile") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
        ) {
            HorizontalDivider()

            println("profileUiState: $profileUiState")

            when {

                !isUserLoggedIn() -> {
                    NotLoggedInScreen(
                        onLogin = onLogin,
                        onSignUp = {}
                    )
                }

                profileUiState.isLoading -> {
                    LoadingScreen()
                }

                profileUiState.error != null -> {
                    ErrorContent()
                }

                profileUiState.data != null && isUserLoggedIn() -> {
                    ProfileContent(
                        user = profileUiState.data,
                        onEditProfile = onEditProfile,
                        onLogout = onLogout
                    )
                }
            }
        }
    }
}

@Composable
fun NotLoggedInScreen(onLogin: () -> Unit, onSignUp: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))

        Image(
            painter = painterResource(resource = Res.drawable.profile_dummy),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .border(
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                    width = 1.dp,
                    shape = CircleShape,
                )
                .clip(shape = CircleShape)
                .background(MaterialTheme.colorScheme.outline)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "You're not logged in",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        Text(
            text = "Log in to view your profile and saved recipes.",
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground)
        )

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = onLogin,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Log In",
                style = MaterialTheme.typography.titleSmall.copy(color = Color.White)
            )
        }

        Spacer(Modifier.height(8.dp))

        OutlinedButton(
            onClick = onSignUp,
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primaryContainer),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
fun ProfileContent(user: User, onEditProfile: () -> Unit, onLogout: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))

        Image(
            painter = painterResource(resource = Res.drawable.avatar),
            contentDescription = "Profile Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .border(
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                    width = 1.dp,
                    shape = CircleShape,
                )
                .clip(shape = CircleShape)
                .background(MaterialTheme.colorScheme.outline)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = user.email,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        Spacer(Modifier.height(24.dp))

        OutlinedButton(
            onClick = onEditProfile,
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primaryContainer),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Edit Profile",
                style = MaterialTheme.typography.titleSmall
            )
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Logout",
                style = MaterialTheme.typography.titleSmall.copy(color = Color.White)
            )
        }
    }
}
