package com.example.composecampgroup4.presentation.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composecampgroup4.presentation.core.base.BaseContentLayout
import com.example.composecampgroup4.presentation.screens.home.screen_handling.HomeUiEvent
import com.example.composecampgroup4.presentation.screens.home.screen_handling.HomeUiState
import com.example.composecampgroup4.presentation.theme.ComposeCampGroup4Theme

@Composable
fun HomeScreenRoot() {
    val viewModel: HomeViewModel = viewModel()

    BaseContentLayout(viewModel = viewModel) { uiState ->
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