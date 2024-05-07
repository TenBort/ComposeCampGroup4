package com.example.composecampgroup4.presentation.screens.details.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecampgroup4.R
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsUiEvent
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsUiState
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsUiState.EditButtonState.*

@Composable
fun Comment(
    modifier: Modifier = Modifier,
    uiState: DetailsUiState,
    onEvent: (DetailsUiEvent) -> Unit,
) {
    val commentTitle = stringResource(R.string.your_comment)
    val comment = uiState.comment
    val commentEdited = uiState.commentEdited
    val title = when (val buttonState = uiState.editButtonState) {
        Add -> buttonState.title
        Edit -> buttonState.title
        Save -> buttonState.title
    }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = commentEdited) {
        if (commentEdited) focusRequester.requestFocus()
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "$commentTitle:",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.outline
        )

        if (comment.isNotBlank() || commentEdited) {
            Spacer(modifier = Modifier.height(12.dp))
            CommentTextField(
                comment = comment,
                onValueChange = { onEvent(DetailsUiEvent.CommentsChanged(it)) },
                enabled = commentEdited,
                focusRequester = focusRequester
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (!uiState.jar.closed) {
            EditCommentTextButton(
                titleRes = title,
                onClick = { onEvent(DetailsUiEvent.EditButtonClicked) }
            )
        }
    }
}

@Composable
private fun CommentTextField(
    modifier: Modifier = Modifier,
    comment: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    focusRequester: FocusRequester
) {
    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = comment,
        onValueChange = onValueChange,
        enabled = enabled,
        textStyle = TextStyle(color = MaterialTheme.colorScheme.outline, lineHeight = 20.sp),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
    )
}

@Composable
private fun EditCommentTextButton(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    onClick: () -> Unit
) {
    Text(
        modifier = modifier.clickable { onClick() },
        text = stringResource(id = titleRes),
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.primary
    )
}