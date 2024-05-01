package com.example.composecampgroup4.presentation.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composecampgroup4.R
import com.example.composecampgroup4.navigation.NavigationState
import com.example.composecampgroup4.navigation.Screen
import com.example.composecampgroup4.presentation.core.base.BaseContentLayout
import com.example.composecampgroup4.presentation.screens.home.screen_handling.HomeUiEvent
import com.example.composecampgroup4.presentation.screens.home.screen_handling.HomeUiState
import com.example.composecampgroup4.presentation.theme.ComposeCampGroup4Theme

@Composable
fun HomeScreenRoot(navigationState: NavigationState) {
    val viewModel: HomeViewModel = viewModel()

    BaseContentLayout(
        viewModel = viewModel,
        floatingActionButton = {
            FloatingActionButton(onClick = { navigationState.navigateTo(Screen.AddJar.route) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(R.string.add_new_jar))
            }
        }
    ) { uiState ->
        HomeScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit
) {

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