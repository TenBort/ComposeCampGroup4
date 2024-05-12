package com.example.composecampgroup4.presentation.screens.add_jar

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composecampgroup4.R
import com.example.composecampgroup4.navigation.NavigationState
import com.example.composecampgroup4.presentation.core.base.BaseContentLayout
import com.example.composecampgroup4.presentation.core.components.TopBarApp
import com.example.composecampgroup4.presentation.screens.add_jar.components.AddJarLoading
import com.example.composecampgroup4.presentation.screens.add_jar.components.AddJarTextFields
import com.example.composecampgroup4.presentation.screens.add_jar.screen_handling.AddJarActionEvent
import com.example.composecampgroup4.presentation.screens.add_jar.screen_handling.AddJarUiEvent
import com.example.composecampgroup4.presentation.screens.add_jar.screen_handling.AddJarUiState
import com.example.composecampgroup4.presentation.theme.ComposeCampGroup4Theme

@Composable
fun AddJarScreenRoot(navigationState: NavigationState) {
    val viewModel: AddJarViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    BaseContentLayout(
        viewModel = viewModel,
        actionsEventHandler = { context ->
            viewModel.actionEvent.collect { actionEvent ->
                when (actionEvent) {
                    AddJarActionEvent.NavigateBack -> {
                        Toast.makeText(
                            context,
                            context.getString(R.string.collection_successfully_added),
                            Toast.LENGTH_SHORT
                        ).show()
                        navigationState.popBackStack()
                    }

                    is AddJarActionEvent.ShowMessage -> snackbarHostState.showSnackbar(
                        actionEvent.message.asString(context)
                    )
                }
            }
        },
        topBar = {
            TopBarApp(
                title = stringResource(id = R.string.add_new_jar),
                onBackClick = { navigationState.popBackStack() }
            )
        },
        bottomBar = { SaveButton(onEvent = viewModel::onEvent) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { uiState ->
            AddJarScreen(uiState = uiState, onEvent = viewModel::onEvent)
        }
    )
}

@Composable
fun AddJarScreen(
    uiState: AddJarUiState,
    onEvent: (AddJarUiEvent) -> Unit,
) {
    val clipboardManager = LocalClipboardManager.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.RESUMED -> {
                val bufferedText = clipboardManager.getText()?.text
                bufferedText?.let { onEvent(AddJarUiEvent.ValidateBufferedText(it)) }
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.isLoading) {
            AddJarLoading()
        } else {
            AddJarTextFields(uiState = uiState, onEvent = onEvent)
        }
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
        onClick = { onEvent(AddJarUiEvent.SaveButtonClicked) },
        shape = RoundedCornerShape(8.dp)
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