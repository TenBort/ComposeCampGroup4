package com.example.composecampgroup4.presentation.screens.home

import com.example.composecampgroup4.presentation.core.base.BaseViewModel
import com.example.composecampgroup4.presentation.screens.home.screen_handling.HomeActionEvent
import com.example.composecampgroup4.presentation.screens.home.screen_handling.HomeUiEvent
import com.example.composecampgroup4.presentation.screens.home.screen_handling.HomeUiState

typealias BaseHomeViewModel = BaseViewModel<HomeUiState, HomeUiEvent, HomeActionEvent>
class HomeViewModel : BaseHomeViewModel() {
    override val initialState: HomeUiState
        get() = HomeUiState()

    override fun onEvent(event: HomeUiEvent) {
        TODO("Not yet implemented")
    }
}