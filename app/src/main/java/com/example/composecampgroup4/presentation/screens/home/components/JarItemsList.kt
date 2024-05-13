package com.example.composecampgroup4.presentation.screens.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.presentation.screens.home.screen_handling.HomeUiEvent

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.jarItemsList(
    jars: List<Jar>,
    searchRequest: String,
    isJarsFound: Boolean,
    onEvent: (HomeUiEvent) -> Unit
) {
    stickyHeader {
        SearchTextField(
            searchRequest = searchRequest,
            onEvent = onEvent
        )
    }
    if (isJarsFound) {
        items(items = jars, key = { it.jarId }) { jar ->
            SwipeToDeleteContainer(
                item = jar,
                onDelete = { onEvent(HomeUiEvent.DeleteJar(it.jarId)) }
            ) {
                JarItem(jar = jar, onEvent = onEvent)
            }
        }
    } else {
        item {
            EmptyHomeScreen(isSearching = true)
        }
    }
}