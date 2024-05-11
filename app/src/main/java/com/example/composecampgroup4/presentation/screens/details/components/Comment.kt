package com.example.composecampgroup4.presentation.screens.details.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecampgroup4.R
import com.example.composecampgroup4.presentation.core.components.CommentTextField
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsUiEvent
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsUiState
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsUiState.EditButtonState.*

@Composable
fun Comment(
    modifier: Modifier = Modifier,
    uiState: DetailsUiState,
    onEvent: (DetailsUiEvent) -> Unit,
) {
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
        if (comment.isNotBlank() || commentEdited) {
            Text(
                text = if (uiState.commentEdited) {
                    stringResource(id = R.string.your_comment, "")
                } else {
                    stringResource(id = R.string.your_comment, comment)
                },
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.outline,
                lineHeight = 20.sp
            )
            CommentText(
                modifier = Modifier.fillMaxWidth(),
                comment = comment,
                commentEdited = uiState.commentEdited,
                onValueChange = { onEvent(DetailsUiEvent.CommentsChanged(it)) },
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
private fun CommentText(
    modifier: Modifier = Modifier,
    comment: String,
    commentEdited: Boolean,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester
) {
    if (commentEdited) {
        Spacer(modifier = Modifier.height(8.dp))
        CommentTextField(
            modifier = modifier.focusRequester(focusRequester),
            value = comment,
            onValueChange = onValueChange,
            placeholderValue = "Введіть ваш коментар"
        )
    }
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
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.primary
    )
}