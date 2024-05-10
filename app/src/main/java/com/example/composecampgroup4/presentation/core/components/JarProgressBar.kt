package com.example.composecampgroup4.presentation.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun JarProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    isClosed: Boolean
) {
    LinearProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp),
        progress = { if (isClosed) 1f else progress },
        strokeCap = StrokeCap.Round,
        trackColor = MaterialTheme.colorScheme.background,
        color = if (isClosed) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.primary,
    )
}