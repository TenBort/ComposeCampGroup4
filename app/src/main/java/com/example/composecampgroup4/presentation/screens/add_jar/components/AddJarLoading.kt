package com.example.composecampgroup4.presentation.screens.add_jar.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.composecampgroup4.R

@Composable
fun AddJarLoading() {
    Text(
        text = stringResource(R.string.wait_adding_collection),
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal)
    )
    Spacer(modifier = Modifier.height(12.dp))
    CircularProgressIndicator()
}