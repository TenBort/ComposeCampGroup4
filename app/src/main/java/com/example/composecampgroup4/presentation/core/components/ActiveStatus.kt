package com.example.composecampgroup4.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecampgroup4.R

@Composable
fun ActiveStatus(
    modifier: Modifier = Modifier,
    jarClosed: Boolean,
    hasNoGoal: Boolean,
    titleSize: TextUnit = 10.sp,
) {
    val jarTitle =
        if (jarClosed) stringResource(R.string.jar_closed) else stringResource(R.string.jar_active)

    Row(
        modifier = modifier
    ) {
        JarStatus(title = jarTitle, jarClosed = jarClosed, titleSize = titleSize)
        if (hasNoGoal) {
            Spacer(modifier = Modifier.width(12.dp))
            JarStatus(
                title = stringResource(R.string.jar_no_goal),
                jarClosed = jarClosed,
                titleSize = titleSize
            )
        }
    }
}

@Composable
private fun JarStatus(
    modifier: Modifier = Modifier,
    title: String,
    jarClosed: Boolean,
    titleSize: TextUnit
) {
    val backgroundColor = if (jarClosed) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        MaterialTheme.colorScheme.background
    }

    val textColor = if (jarClosed) {
        MaterialTheme.colorScheme.onSurfaceVariant
    } else {
        MaterialTheme.colorScheme.onBackground
    }

    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(50.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = titleSize,
            color = textColor,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}