package com.example.composecampgroup4.presentation.screens.home.screen_handling

import com.example.composecampgroup4.presentation.core.base.common.ActionEvent
import com.example.composecampgroup4.presentation.core.utils.UiText

sealed interface HomeActionEvent : ActionEvent {
    data class ShowMessage(val message: UiText) : HomeActionEvent
    data class NavigateToDetails(val jarId: String) : HomeActionEvent
}