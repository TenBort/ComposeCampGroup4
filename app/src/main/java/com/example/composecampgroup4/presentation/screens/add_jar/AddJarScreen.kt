package com.example.composecampgroup4.presentation.screens.add_jar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composecampgroup4.presentation.core.base.BaseContentLayout
import com.example.composecampgroup4.presentation.screens.add_jar.screen_handling.AddJarUiEvent
import com.example.composecampgroup4.presentation.screens.add_jar.screen_handling.AddJarUiState
import com.example.composecampgroup4.presentation.theme.ComposeCampGroup4Theme

@Composable
fun AddJarScreenRoot() {
    val viewModel: AddJarViewModel = viewModel()

    BaseContentLayout(viewModel = viewModel) { uiState ->
        AddJarScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
fun AddJarScreen(
    uiState: AddJarUiState,
    onEvent: (AddJarUiEvent) -> Unit,
) {

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddJarScreenPreview() {
    ComposeCampGroup4Theme {
        AddJarScreen(
            uiState = AddJarUiState(),
            onEvent = {}
        )
    }
}