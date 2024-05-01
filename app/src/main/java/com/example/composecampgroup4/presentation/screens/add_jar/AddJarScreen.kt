package com.example.composecampgroup4.presentation.screens.add_jar

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composecampgroup4.R
import com.example.composecampgroup4.navigation.NavigationState
import com.example.composecampgroup4.presentation.core.base.BaseContentLayout
import com.example.composecampgroup4.presentation.core.components.TopBarApp
import com.example.composecampgroup4.presentation.screens.add_jar.components.AddLinkTextField
import com.example.composecampgroup4.presentation.screens.add_jar.components.CommentTextField
import com.example.composecampgroup4.presentation.screens.add_jar.screen_handling.AddJarActionEvent
import com.example.composecampgroup4.presentation.screens.add_jar.screen_handling.AddJarUiEvent
import com.example.composecampgroup4.presentation.screens.add_jar.screen_handling.AddJarUiState
import com.example.composecampgroup4.presentation.theme.ComposeCampGroup4Theme

@Composable
fun AddJarScreenRoot(navigationState: NavigationState) {
    val viewModel: AddJarViewModel = hiltViewModel()

    BaseContentLayout(
        viewModel = viewModel,
        actionsEventHandler = { context ->
            viewModel.actionEvent.collect { actionEvent ->
                when (actionEvent) {
                    AddJarActionEvent.NavigateBack -> navigationState.popBackStack()
                    is AddJarActionEvent.Error -> {
                        Log.d("ActionEventError", actionEvent.errorMessage.asString(context))
                    }
                }
            }
        },
        topBar = {
            TopBarApp(
                title = stringResource(id = R.string.add_new_jar),
                onBackClick = { navigationState.popBackStack() }
            )
        },
        bottomBar = { SaveButton(onEvent = viewModel::onEvent) }
    ) { uiState ->
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
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AddLinkTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.link,
            onEvent = onEvent
        )
        Spacer(modifier = Modifier.height(12.dp))
        CommentTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.comment,
            onEvent = onEvent
        )
    }
}

@Composable
private fun SaveButton(
    onEvent: (AddJarUiEvent) -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
            .height(50.dp),
        onClick = { onEvent(AddJarUiEvent.SaveButtonClicked) }
    ) {
        Text(stringResource(R.string.save))
    }
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