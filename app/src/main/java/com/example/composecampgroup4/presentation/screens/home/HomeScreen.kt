package com.example.composecampgroup4.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.example.composecampgroup4.presentation.screens.home.components.SearchTextField
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
            onEvent = viewModel::onEvent
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit
) {
    if (uiState.jars.isEmpty() && !uiState.isSearching) {
        EmptyHomeScreen(isSearching = false)
    } else {
        Column(modifier = modifier) {
            PullToRefreshLazyColumn(
                isRefreshing = uiState.isRefreshing,
                onRefresh = { onEvent(HomeUiEvent.RefreshStarted) }
            ) {
                stickyHeader {
                    SearchTextField(
                        searchRequest = uiState.searchRequest,
                        onEvent = onEvent
                    )
                }

                items(items = uiState.jars, key = { it.jarId }) { jar ->

                    // If the search does not find anything, it displays an empty screen with a message that no jars were found
                    if (uiState.jars.isEmpty() && uiState.isSearching) {
                        EmptyHomeScreen(isSearching = true)
                    } else {

                        // TODO: Remove this card, and implement yours
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = "${jar.ownerName} створив збір",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = jar.title,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }

                                IconButton(onClick = { onEvent(HomeUiEvent.JarFavouriteChanged(jar = jar)) }) {
                                    Icon(
                                        imageVector = if (jar.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeTopBar(
    onEvent: (HomeUiEvent) -> Unit
) {
    TopBarApp(
        title = "Назва додатку",
        actions = {
            IconButton(onClick = { onEvent(HomeUiEvent.RefreshStarted) }) {
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
            onEvent = { }
        )
    }
}