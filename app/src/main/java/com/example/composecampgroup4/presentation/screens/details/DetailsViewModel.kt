package com.example.composecampgroup4.presentation.screens.details

import com.example.composecampgroup4.data.repository.JarDatabaseRepositoryImpl
import com.example.composecampgroup4.presentation.core.base.BaseViewModel
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsActionEvent
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsUiEvent
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsUiState
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsUiState.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

typealias BaseDetailsViewModel = BaseViewModel<DetailsUiState, DetailsUiEvent, DetailsActionEvent>

@HiltViewModel(assistedFactory = DetailsViewModel.DetailsViewModelFactory::class)
class DetailsViewModel @AssistedInject constructor(
    @Assisted val jarId: String,
    private val jarDatabaseRepository: JarDatabaseRepositoryImpl
) : BaseDetailsViewModel() {
    override val initialState: DetailsUiState
        get() = DetailsUiState()

    init {
        launch {
            jarDatabaseRepository.getJar(jarId).collect { jar ->
                updateState { it.copy(jar = jar) }
                updateComment(jar.userComment)
                setEditButtonState()
            }
        }
    }

    override fun onEvent(event: DetailsUiEvent) {
        when(event) {
            DetailsUiEvent.EditButtonClicked -> {
                saveComment()
                updateEditedState()
                setEditButtonState()
            }

            is DetailsUiEvent.CommentsChanged -> updateComment(event.comment)
        }
    }

    private fun saveComment() {
        if (currentState.editButtonState is EditButtonState.Save) {
            launch {
                jarDatabaseRepository.upsertJar(currentState.jar.copy(userComment = currentState.comment))
            }
        }
    }

    private fun setEditButtonState() {
        if (currentState.commentEdited) {
            updateButtonState(EditButtonState.Save)
            return
        }
        if (currentState.jar.userComment.isBlank()) {
            updateButtonState(EditButtonState.Add)
        } else {
            updateButtonState(EditButtonState.Edit)
        }
    }

    private fun updateComment(comment: String) = updateState { it.copy(comment = comment.trim()) }

    private fun updateEditedState() {
        updateState {
            it.copy(commentEdited = !currentState.commentEdited)
        }
    }

    private fun updateButtonState(buttonState: EditButtonState) {
        updateState { it.copy(editButtonState = buttonState) }
    }
    @AssistedFactory
    interface DetailsViewModelFactory {
        fun create(jarId: String): DetailsViewModel
    }
}