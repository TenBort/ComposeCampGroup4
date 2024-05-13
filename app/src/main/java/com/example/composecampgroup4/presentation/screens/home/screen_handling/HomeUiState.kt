package com.example.composecampgroup4.presentation.screens.home.screen_handling

import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.presentation.core.base.common.UiState

data class HomeUiState(

    val jars: List<Jar> = emptyList(),
    val searchRequest: String = "",
    val isSearching: Boolean = false,
    val isRefreshing: Boolean = false,
    val isJarsFound: Boolean = false,
    val contentState: ContentState = ContentState.Loading

) : UiState {

    val isInitJarLoaded: Boolean = (!isSearching && jars.isNotEmpty())

    sealed interface ContentState {
        data object Loading : ContentState

        data object EmptyJars : ContentState

        data object ShowJars : ContentState
    }
}