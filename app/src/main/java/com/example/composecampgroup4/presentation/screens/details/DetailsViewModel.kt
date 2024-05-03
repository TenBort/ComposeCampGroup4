package com.example.composecampgroup4.presentation.screens.details

import com.example.composecampgroup4.data.repository.JarDatabaseRepositoryImpl
import com.example.composecampgroup4.presentation.core.base.BaseViewModel
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsActionEvent
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsUiEvent
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers

typealias  BaseDetailsViewModel = BaseViewModel<DetailsUiState, DetailsUiEvent, DetailsActionEvent>

@HiltViewModel(assistedFactory = DetailsViewModel.DetailsViewModelFactory::class)
class DetailsViewModel @AssistedInject constructor(
    @Assisted val jarId: String,
    private val jarDatabaseRepository: JarDatabaseRepositoryImpl
) : BaseDetailsViewModel() {
    override val initialState: DetailsUiState
        get() = DetailsUiState()

    init {
        launch(Dispatchers.IO) {
            val jar = jarDatabaseRepository.getJar(jarId)
            updateState { it.copy(jar = jar) }
        }
    }

    override fun onEvent(event: DetailsUiEvent) {
        TODO("Not yet implemented")
    }

    @AssistedFactory
    interface DetailsViewModelFactory {
        fun create(jarId: String): DetailsViewModel
    }
}