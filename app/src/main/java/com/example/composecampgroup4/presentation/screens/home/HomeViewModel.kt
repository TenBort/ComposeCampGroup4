package com.example.composecampgroup4.presentation.screens.home

import androidx.lifecycle.viewModelScope
import com.example.composecampgroup4.R
import com.example.composecampgroup4.data.repository.JarDatabaseRepositoryImpl
import com.example.composecampgroup4.data.repository.JarNetworkRepositoryImpl
import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.domain.entity.Result
import com.example.composecampgroup4.domain.entity.errors.JarDataError
import com.example.composecampgroup4.presentation.core.base.BaseViewModel
import com.example.composecampgroup4.presentation.core.utils.UiText
import com.example.composecampgroup4.presentation.core.utils.asUiText
import com.example.composecampgroup4.presentation.screens.home.screen_handling.HomeActionEvent
import com.example.composecampgroup4.presentation.screens.home.screen_handling.HomeUiEvent
import com.example.composecampgroup4.presentation.screens.home.screen_handling.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import javax.inject.Inject

typealias BaseHomeViewModel = BaseViewModel<HomeUiState, HomeUiEvent, HomeActionEvent>

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val jarDatabaseRepository: JarDatabaseRepositoryImpl,
    private val jarNetworkRepository: JarNetworkRepositoryImpl,
) : BaseHomeViewModel() {
    override val initialState: HomeUiState
        get() = HomeUiState()

    init {
        launch {
            jarDatabaseRepository.getAllJars().collect { jarList ->
                if (currentState.isSearching) {
                    searchJar(currentState.searchRequest)
                }
                updateJarList(jarList)
            }
        }
    }

    override fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.JarFavouriteChanged -> updateJarFavourite(event.jar)
            is HomeUiEvent.SearchChanged -> updateSearchRequest(event.search)
            HomeUiEvent.RefreshStarted -> refreshJarList()
            is HomeUiEvent.DeleteJar -> deleteJar(event.jarId)
        }
    }

    private fun refreshJarList() {
        launch {
            updateRefreshState(true)

            val refreshJob = launch {
                val loadedResult = jarsLoadResult()
                val updatedJars = mutableListOf<Jar>()
                var isError = false
                for (result in loadedResult) {
                    when(result) {
                        is Result.Error -> {
                            sendMessage(result.error.asUiText())
                            isError = true
                            break
                        }
                        is Result.Success -> updatedJars.add(result.data)
                    }
                }

                if(!isError) {
                    updateJarList(updatedJars)
                    sendMessage(UiText.StringResource(R.string.jars_refreshed))
                }
            }

            refreshJob.join()
            updateRefreshState(false)
        }
    }

    private suspend fun jarsLoadResult(): List<Result<Jar, JarDataError>> {
        val jarsDeferred = mutableListOf<Deferred<Result<Jar, JarDataError>>>()

        currentState.jars.forEach { jar ->
            val resultDeferred = viewModelScope.async {
                jarNetworkRepository.loadJarData(longJarId = jar.longJarId, ownerName = jar.ownerName)
            }

            jarsDeferred.add(resultDeferred)
        }

        return jarsDeferred.awaitAll()
    }

    private fun searchJar(searchRequest: String) {
        launch {
            val foundJars = jarDatabaseRepository.searchJar(searchRequest)
            updateJarList(foundJars)
        }
    }

    private fun deleteJar(jarId: String) {
        launch {
            jarDatabaseRepository.deleteJar(jarId)
        }
    }

    private fun updateJarList(jarList: List<Jar>) = updateState { it.copy(jars = jarList) }

    private fun updateSearchRequest(searchRequest: String) {
        updateSearchState(searchRequest.isNotBlank())
        updateState { it.copy(searchRequest = searchRequest) }
        searchJar(searchRequest)
    }

    private fun updateSearchState(isSearching: Boolean) =
        updateState { it.copy(isSearching = isSearching) }

    private fun updateRefreshState(isRefreshing: Boolean) = updateState { it.copy(isRefreshing = isRefreshing) }


    private fun updateJarFavourite(jar: Jar) {
        launch {
            val updateJar = jar.copy(isFavourite = !jar.isFavourite)
            jarDatabaseRepository.upsertJar(updateJar)
        }
    }

    private fun sendMessage(message: UiText) {
        sendActionEvent(HomeActionEvent.ShowMessage(message))
    }
}