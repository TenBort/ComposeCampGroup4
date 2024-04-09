package com.example.composecampgroup4.presentation.screens.add_jar

import com.example.composecampgroup4.presentation.core.base.BaseViewModel
import com.example.composecampgroup4.presentation.screens.add_jar.screen_handling.AddJarActionEvent
import com.example.composecampgroup4.presentation.screens.add_jar.screen_handling.AddJarUiEvent
import com.example.composecampgroup4.presentation.screens.add_jar.screen_handling.AddJarUiState

typealias  BaseAddJarViewModel = BaseViewModel<AddJarUiState, AddJarUiEvent, AddJarActionEvent>
class AddJarViewModel : BaseAddJarViewModel() {
    override val initialState: AddJarUiState
        get() = AddJarUiState()

    override fun onEvent(event: AddJarUiEvent) {
        TODO("Not yet implemented")
    }

}