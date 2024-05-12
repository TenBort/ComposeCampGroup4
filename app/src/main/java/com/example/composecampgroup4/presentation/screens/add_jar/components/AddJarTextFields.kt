package com.example.composecampgroup4.presentation.screens.add_jar.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.composecampgroup4.R
import com.example.composecampgroup4.presentation.core.components.CommentTextField
import com.example.composecampgroup4.presentation.core.components.OutlinedTextFieldWithEndCursor
import com.example.composecampgroup4.presentation.screens.add_jar.screen_handling.AddJarUiEvent
import com.example.composecampgroup4.presentation.screens.add_jar.screen_handling.AddJarUiState

@Composable
fun ColumnScope.AddJarTextFields(
    uiState: AddJarUiState,
    onEvent: (AddJarUiEvent) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var focusDirection by remember { mutableStateOf(FocusDirection.Previous) }

    LaunchedEffect(focusDirection) {
        focusManager.moveFocus(focusDirection)
    }

    LaunchedEffect(uiState.link != "") {
        focusRequester.requestFocus()
    }

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
        moveFocus = { focusDirection = it },
        onValueChange = { onEvent(AddJarUiEvent.LinkChanged(it)) }
    )

    Spacer(modifier = Modifier.height(12.dp))

    CommentTextField(
        modifier = Modifier.fillMaxWidth(),
        value = uiState.comment,
        onValueChange = { onEvent(AddJarUiEvent.CommentChanged(it)) }
    )
}

@Composable
private fun AddLinkTextField(
    modifier: Modifier = Modifier,
    value: String,
    moveFocus: (FocusDirection) -> Unit,
    onValueChange: (String) -> Unit
) {
    OutlinedTextFieldWithEndCursor(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = stringResource(R.string.link_to_jar)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = {
            moveFocus(FocusDirection.Down)
        })
    )
}