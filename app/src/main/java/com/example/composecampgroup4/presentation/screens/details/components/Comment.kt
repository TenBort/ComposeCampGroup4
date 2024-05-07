package com.example.composecampgroup4.presentation.screens.details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecampgroup4.R

@Composable
fun Comment(modifier: Modifier = Modifier, jarClosed: Boolean, comment: String) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        val yourComment = stringResource(R.string.your_comment)
        Text(
            text = "$yourComment:",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = comment,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.jar_comment_edit),
            fontSize = 12.sp,
            color = if (jarClosed) MaterialTheme.colorScheme.outline
            else MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { }
        )
    }
}