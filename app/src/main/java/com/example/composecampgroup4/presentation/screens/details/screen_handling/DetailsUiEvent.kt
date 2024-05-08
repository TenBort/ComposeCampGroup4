package com.example.composecampgroup4.presentation.screens.details.screen_handling

import com.example.composecampgroup4.presentation.core.base.common.UiEvent

sealed interface DetailsUiEvent : UiEvent {

    data class CommentsChanged(val comment: String) : DetailsUiEvent

    data object EditButtonClicked : DetailsUiEvent
}