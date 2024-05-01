package com.example.composecampgroup4.presentation.screens.add_jar.screen_handling

import com.example.composecampgroup4.presentation.core.base.common.ActionEvent
import com.example.composecampgroup4.presentation.core.utils.UiText

sealed interface AddJarActionEvent : ActionEvent {
    data object NavigateBack : AddJarActionEvent

    data class Error(val errorMessage: UiText) : AddJarActionEvent
}