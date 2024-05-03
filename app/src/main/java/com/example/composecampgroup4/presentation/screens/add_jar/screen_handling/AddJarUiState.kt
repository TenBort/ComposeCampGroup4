package com.example.composecampgroup4.presentation.screens.add_jar.screen_handling

import com.example.composecampgroup4.presentation.core.base.common.UiState

data class AddJarUiState(
    val link: String = "",
    val comment: String = "",
    val isLoading: Boolean = false
) : UiState