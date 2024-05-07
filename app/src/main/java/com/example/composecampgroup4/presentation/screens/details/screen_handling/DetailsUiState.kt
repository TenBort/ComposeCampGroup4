package com.example.composecampgroup4.presentation.screens.details.screen_handling

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import com.example.composecampgroup4.R
import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.presentation.core.base.common.UiState

@Stable
data class DetailsUiState(
    val jar: Jar = Jar(),
    val comment: String = "",
    val commentEdited: Boolean = false,
    val editButtonState: EditButtonState = EditButtonState.Edit
) : UiState {
    sealed class EditButtonState(@StringRes val title: Int) {
        data object Edit : EditButtonState(R.string.jar_comment_edit)
        data object Add : EditButtonState(R.string.jar_comment_add)
        data object Save : EditButtonState(R.string.jar_comment_save)
    }
}