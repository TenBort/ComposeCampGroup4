package com.example.composecampgroup4.presentation.screens.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecampgroup4.R

@Composable
fun TitleJarInfo(
    modifier: Modifier = Modifier,
    title: String,
    ownerName: String,
    description: String,
    jarClosed: Boolean,
    goal: Long
) {
    Box(
        modifier = modifier
            .fillMaxWidth(), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(R.string.collected_by_owner_name, ownerName),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(top = 4.dp),
            )

            IsActiveStatus(
                modifier = Modifier.padding(top = 8.dp),
                jarClosed = jarClosed,
                goal = goal
            )


            if (description.isNotEmpty()) {
                Text(
                    text = description,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = modifier.padding(top = 20.dp)
                )
            }
        }
    }
}

@Composable
private fun IsActiveStatus(
    modifier: Modifier = Modifier,
    jarClosed: Boolean,
    goal: Long
) {
    val jarTitle =
        if (jarClosed) stringResource(R.string.jar_closed) else stringResource(R.string.jar_active)

    Row(
        modifier = modifier
    ) {
        JarStatus(title = jarTitle, jarClosed = jarClosed)
        if (goal == 0L) {
            Spacer(modifier = Modifier.width(12.dp))
            JarStatus(title = stringResource(R.string.jar_no_goal), jarClosed = jarClosed)
        }
    }
}

@Composable
private fun JarStatus(
    modifier: Modifier = Modifier,
    title: String,
    jarClosed: Boolean
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
            fontSize = 12.sp,
            color = textColor,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}