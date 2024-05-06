package com.example.composecampgroup4.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.composecampgroup4.R
import com.example.composecampgroup4.presentation.screens.home.screen_handling.HomeUiEvent

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    searchRequest: String,
    onEvent: (HomeUiEvent) -> Unit
) {
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = searchRequest,
            onValueChange = { onEvent(HomeUiEvent.SearchChanged(it)) },
            label = { Text(text = stringResource(R.string.search)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.jar_search),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
            ),
            trailingIcon = {
                if (searchRequest.isNotBlank()) {
                    IconButton(onClick = { onEvent(HomeUiEvent.SearchChanged("")) }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(R.string.clear_search_field),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            },
            shape = RoundedCornerShape(100.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))
    }
}