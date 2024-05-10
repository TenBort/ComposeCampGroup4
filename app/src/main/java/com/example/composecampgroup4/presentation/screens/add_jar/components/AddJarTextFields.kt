package com.example.composecampgroup4.presentation.screens.add_jar.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.composecampgroup4.R
import com.example.composecampgroup4.presentation.screens.add_jar.screen_handling.AddJarUiEvent
import com.example.composecampgroup4.presentation.screens.add_jar.screen_handling.AddJarUiState

@Composable
fun ColumnScope.AddJarTextFields(
    uiState: AddJarUiState,
    onEvent: (AddJarUiEvent) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    SideEffect { focusRequester.requestFocus() }

    Text(
        modifier = Modifier.align(Alignment.Start),
        text = "Ваше посилання на банку:",
        style = MaterialTheme.typography.titleMedium
    )

    Spacer(modifier = Modifier.height(12.dp))

    AddLinkTextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = uiState.link,
        focusManager = focusManager,
        onEvent = onEvent
    )

    Spacer(modifier = Modifier.height(12.dp))

    CommentTextField(
        modifier = Modifier.fillMaxWidth(),
        value = uiState.comment,
        focusManager = focusManager,
        onEvent = onEvent
    )
}

@Composable
private fun AddLinkTextField(
    modifier: Modifier = Modifier,
    value: String,
    focusManager: FocusManager,
    onEvent: (AddJarUiEvent) -> Unit
) {

    OutlinedTextField(
        modifier = modifier,
        value = TextFieldValue(value, selection = TextRange(value.length)),
        onValueChange = { onEvent(AddJarUiEvent.LinkChanged(it.text)) },
        placeholder = { Text(text = stringResource(R.string.link_to_jar)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = {
            focusManager.moveFocus(FocusDirection.Down)
        })
    )
}

@Composable
private fun CommentTextField(
    modifier: Modifier = Modifier,
    value: String,
    focusManager: FocusManager,
    onEvent: (AddJarUiEvent) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onEvent(AddJarUiEvent.CommentChanged(it)) },
        placeholder = { Text(text = stringResource(R.string.your_comment)) },
        minLines = 3,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        })
    )
}