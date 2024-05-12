package com.example.composecampgroup4.presentation.screens.add_jar

import androidx.compose.runtime.Stable
import com.example.composecampgroup4.R
import com.example.composecampgroup4.data.repository.JarDatabaseRepositoryImpl
import com.example.composecampgroup4.data.repository.JarNetworkRepositoryImpl
import com.example.composecampgroup4.domain.JarLinkValidator
import com.example.composecampgroup4.domain.entity.Result
import com.example.composecampgroup4.presentation.core.base.BaseViewModel
import com.example.composecampgroup4.presentation.core.utils.UiText
import com.example.composecampgroup4.presentation.core.utils.asUiText
import com.example.composecampgroup4.presentation.screens.add_jar.screen_handling.AddJarActionEvent
import com.example.composecampgroup4.presentation.screens.add_jar.screen_handling.AddJarUiEvent
import com.example.composecampgroup4.presentation.screens.add_jar.screen_handling.AddJarUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

typealias BaseAddJarViewModel = BaseViewModel<AddJarUiState, AddJarUiEvent, AddJarActionEvent>

@Stable
@HiltViewModel
class AddJarViewModel @Inject constructor(
    private val jarNetworkRepository: JarNetworkRepositoryImpl,
    private val jarDatabaseRepository: JarDatabaseRepositoryImpl,
    private val jarLinkValidator: JarLinkValidator
) : BaseAddJarViewModel() {

    override val initialState: AddJarUiState
        get() = AddJarUiState()


    override fun onEvent(event: AddJarUiEvent) {
        when (event) {
            is AddJarUiEvent.CommentChanged -> updateComment(event.comment)
            is AddJarUiEvent.LinkChanged -> updateLink(event.link)
            AddJarUiEvent.SaveButtonClicked -> saveJar()
            is AddJarUiEvent.ValidateBufferedText -> updateLinkFromBuffer(event.text)
        }
    }

    private fun saveJar() {
        when (val result = jarLinkValidator.validateLink(currentState.link)) {
            is Result.Error -> sendMessage(result.error.asUiText())
            is Result.Success -> upsertJar(result.data)
        }
    }

    private fun upsertJar(jarId: String) {
        updateLoadingState(true)
        launch {
            when (val result = jarNetworkRepository.loadJarData(jarId)) {
                is Result.Error -> {
                    updateLoadingState(false)
                    sendMessage(result.error.asUiText())
                }

                is Result.Success -> {
                    jarDatabaseRepository.upsertJar(result.data)
                    sendActionEvent(AddJarActionEvent.NavigateBack)
                }
            }
        }
    }

    private fun updateLink(link: String) = updateState { it.copy(link = link) }

    private fun updateComment(comment: String) = updateState { it.copy(comment = comment) }

    private fun updateLoadingState(isLoading: Boolean) =
        updateState { it.copy(isLoading = isLoading) }

    private fun updateLinkFromBuffer(text: String) {
        val result = jarLinkValidator.validateLink(text)
        if (result is Result.Success && currentState.link != "https://send.monobank.ua/jar/${result.data}") {
            updateLink(text)
            sendMessage(UiText.StringResource(R.string.add_link_message))
        }
    }

    private fun sendMessage(message: UiText) {
        sendActionEvent(AddJarActionEvent.ShowMessage(message))
    }
}