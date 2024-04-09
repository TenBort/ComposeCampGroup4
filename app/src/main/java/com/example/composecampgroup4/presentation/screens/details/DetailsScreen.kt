package com.example.composecampgroup4.presentation.screens.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composecampgroup4.presentation.core.base.BaseContentLayout
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsUiEvent
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsUiState
import com.example.composecampgroup4.presentation.theme.ComposeCampGroup4Theme

@Composable
fun DetailsScreenRoot() {
    val viewModel: DetailsViewModel = viewModel()

    BaseContentLayout(
        viewModel = viewModel
    ) { uiState ->
        DetailsScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
fun DetailsScreen(
    uiState: DetailsUiState,
    onEvent: (DetailsUiEvent) -> Unit
) {

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetailsScreenPreview() {
    ComposeCampGroup4Theme {
        DetailsScreen(
            uiState = DetailsUiState(),
            onEvent = { }
        )
    }
}