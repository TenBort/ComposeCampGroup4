package com.example.composecampgroup4.presentation.screens.details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecampgroup4.R
import com.example.composecampgroup4.presentation.core.components.ActiveStatus

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

            ActiveStatus(
                modifier = Modifier.padding(top = 8.dp),
                jarClosed = jarClosed,
                hasNoGoal = goal == 0L
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