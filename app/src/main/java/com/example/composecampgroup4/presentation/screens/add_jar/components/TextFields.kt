package com.example.composecampgroup4.presentation.screens.add_jar.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.composecampgroup4.R
import com.example.composecampgroup4.presentation.screens.add_jar.screen_handling.AddJarUiEvent

@Composable
fun AddLinkTextField(
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
fun CommentTextField(
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