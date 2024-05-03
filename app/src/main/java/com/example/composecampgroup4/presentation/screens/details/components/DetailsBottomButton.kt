package com.example.composecampgroup4.presentation.screens.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileCopy
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.composecampgroup4.R

@Composable
fun DetailsBottomButton(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
    onCopyClick: () -> Unit
) {
    Row(modifier = modifier.background(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp))) {
        Button(
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            onClick = onButtonClick,
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Задонатити")
        }
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = onCopyClick) {
            Icon(
                imageVector = Icons.Outlined.FileCopy,
                contentDescription = stringResource(R.string.copy_jar_link)
            )
        }
    }
}