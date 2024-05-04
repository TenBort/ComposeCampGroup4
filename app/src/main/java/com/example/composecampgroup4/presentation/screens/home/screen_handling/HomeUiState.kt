package com.example.composecampgroup4.presentation.screens.home.screen_handling

import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.presentation.core.base.common.UiState

data class HomeUiState(
    val jars: List<Jar> = emptyList()
) : UiState