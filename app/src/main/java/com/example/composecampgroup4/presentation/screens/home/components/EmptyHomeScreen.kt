package com.example.composecampgroup4.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.composecampgroup4.R

@Composable
fun EmptyHomeScreen(
    modifier: Modifier = Modifier,
    isSearching: Boolean = false,
    isLoading: Boolean = false
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else if (isSearching) {
            Text(
                text = stringResource(R.string.jars_not_founded),
                style = MaterialTheme.typography.titleMedium
            )
        } else {
            Text(
                text = stringResource(R.string.empty_jar_list),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(R.string.add_new_link),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

}