package com.example.composecampgroup4.presentation.screens.home.screen_handling

import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.presentation.core.base.common.UiEvent

sealed interface HomeUiEvent : UiEvent {
    data class SearchChanged(val search: String) : HomeUiEvent

    data class JarFavouriteChanged(val jar: Jar) : HomeUiEvent

    data class DeleteJar(val jarId: String) : HomeUiEvent

    data object RefreshStarted : HomeUiEvent
}