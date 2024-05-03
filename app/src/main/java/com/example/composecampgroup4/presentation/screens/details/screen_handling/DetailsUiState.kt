package com.example.composecampgroup4.presentation.screens.details.screen_handling

import androidx.compose.runtime.Stable
import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.presentation.core.base.common.UiState

@Stable
data class DetailsUiState(
    val jar: Jar = Jar()
) : UiState