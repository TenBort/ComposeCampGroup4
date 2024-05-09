package com.example.composecampgroup4.presentation.core.components

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.composecampgroup4.R

@Composable
fun OwnerJarImage(
    modifier: Modifier = Modifier,
    imageUri: Uri,
    isClosed: Boolean
) {
    Card(
        modifier = modifier,
        shape = CircleShape,
        border = BorderStroke(
            width = 1.dp,
            color = if (isClosed) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUri)
                .crossfade(true)
                .placeholder(R.mipmap.ic_launcher)
                .build(),
            contentDescription = "Зображення власника збору",
            contentScale = ContentScale.Crop
        )
    }
}