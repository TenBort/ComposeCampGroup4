package com.example.composecampgroup4.presentation.screens.home.screen_handling

import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.presentation.core.base.common.UiState

data class HomeUiState(
    val jarList: List<Jar> = listOf(),
    val searchRequest: String = "",
    val isSearching: Boolean = false,
    val isRefreshing: Boolean = false
) : UiState