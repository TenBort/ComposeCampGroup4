package com.example.composecampgroup4.presentation.screens.add_jar.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.composecampgroup4.R
import com.example.composecampgroup4.presentation.screens.add_jar.screen_handling.AddJarUiEvent
import com.example.composecampgroup4.presentation.screens.add_jar.screen_handling.AddJarUiState

@Composable
fun AddJarTextFields(
    uiState: AddJarUiState,
    onEvent: (AddJarUiEvent) -> Unit
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

@Composable
private fun AddLinkTextField(
    modifier: Modifier = Modifier,
    value: String,
    onEvent: (AddJarUiEvent) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onEvent(AddJarUiEvent.LinkChanged(it)) },
        placeholder = { Text(text = stringResource(R.string.link_to_jar)) }
    )
}

@Composable
private fun CommentTextField(
    modifier: Modifier = Modifier,
    value: String,
    onEvent: (AddJarUiEvent) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onEvent(AddJarUiEvent.CommentChanged(it)) },
        placeholder = { Text(text = stringResource(R.string.your_comment)) },
        minLines = 3
    )
}