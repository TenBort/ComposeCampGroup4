package com.example.composecampgroup4.presentation.screens.add_jar.screen_handling

import com.example.composecampgroup4.presentation.core.base.common.UiEvent

sealed interface AddJarUiEvent : UiEvent {
    data class LinkChanged(val link: String) : AddJarUiEvent
    data class CommentChanged(val comment: String) : AddJarUiEvent
    data object SaveButtonClicked : AddJarUiEvent
    data class ValidateBufferedText(val text: String) : AddJarUiEvent
}