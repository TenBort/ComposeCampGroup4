package com.example.composecampgroup4.presentation.screens.details

import com.example.composecampgroup4.presentation.core.base.BaseViewModel
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsActionEvent
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsUiEvent
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsUiState

typealias  BaseDetailsViewModel = BaseViewModel<DetailsUiState, DetailsUiEvent, DetailsActionEvent>
class DetailsViewModel : BaseDetailsViewModel() {
    override val initialState: DetailsUiState
        get() = DetailsUiState()

    override fun onEvent(event: DetailsUiEvent) {
        TODO("Not yet implemented")
    }

}