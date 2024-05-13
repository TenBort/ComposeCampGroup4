package com.example.composecampgroup4.presentation.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composecampgroup4.R
import com.example.composecampgroup4.navigation.NavigationState
import com.example.composecampgroup4.navigation.Screen
import com.example.composecampgroup4.presentation.core.base.BaseContentLayout
import com.example.composecampgroup4.presentation.core.components.PullToRefreshLazyColumn
import com.example.composecampgroup4.presentation.core.components.TopBarApp
import com.example.composecampgroup4.presentation.screens.home.components.EmptyHomeScreen
import com.example.composecampgroup4.presentation.screens.home.components.jarItemsList
import com.example.composecampgroup4.presentation.screens.home.screen_handling.HomeActionEvent
import com.example.composecampgroup4.presentation.screens.home.screen_handling.HomeUiEvent
import com.example.composecampgroup4.presentation.screens.home.screen_handling.HomeUiState
import com.example.composecampgroup4.presentation.theme.ComposeCampGroup4Theme

@Composable
fun HomeScreenRoot(
    navigationState: NavigationState
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    BaseContentLayout(
        viewModel = viewModel,
        actionsEventHandler = { context ->
            viewModel.actionEvent.collect { actionEvent ->
                when (actionEvent) {
                    is HomeActionEvent.ShowMessage -> snackbarHostState.showSnackbar(
                        actionEvent.message.asString(context)
                    )

                    is HomeActionEvent.NavigateToDetails -> navigationState.navigateToDetails(
                        actionEvent.jarId
                    )
                }
            }
        },
        topBar = { HomeTopBar(onEvent = viewModel::onEvent) },
        floatingActionButton = {
            HomeFAB(onClick = { navigationState.navigateTo(Screen.AddJar.route) })
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { uiState ->
        HomeScreen(
            modifier = Modifier.padding(horizontal = 16.dp),
            uiState = uiState,
            onEvent = viewModel::onEvent,
        )
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
) {
    LaunchedEffect(uiState.isInitJarLoaded) {
        if (uiState.isInitJarLoaded) onEvent(HomeUiEvent.InitRefreshJars)
    }

    when (uiState.contentState) {
        HomeUiState.ContentState.Loading -> {
            EmptyHomeScreen(modifier = modifier, isLoading = true)
        }

        HomeUiState.ContentState.EmptyJars -> {
            EmptyHomeScreen(modifier = modifier, isSearching = false)
        }

        is HomeUiState.ContentState.ShowJars -> {
            PullToRefreshLazyColumn(
                modifier = modifier.fillMaxSize(),
                isRefreshing = uiState.isRefreshing,
                onRefresh = { onEvent(HomeUiEvent.RefreshJars) }
            ) {
                jarItemsList(
                    jars = uiState.jars,
                    searchRequest = uiState.searchRequest,
                    isJarsFound = uiState.isJarsFound,
                    onEvent = onEvent
                )
            }
        }
    }
}

@Composable
private fun HomeTopBar(
    onEvent: (HomeUiEvent) -> Unit
) {
    TopBarApp(
        title = stringResource(R.string.jars_tracker),
        actions = {
            IconButton(onClick = { onEvent(HomeUiEvent.RefreshJars) }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Оновити стан зборів",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    )
}

@Composable
private fun HomeFAB(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.add_new_jar)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    ComposeCampGroup4Theme {
        HomeScreen(
            uiState = HomeUiState(),
            onEvent = { },
        )
    }
}