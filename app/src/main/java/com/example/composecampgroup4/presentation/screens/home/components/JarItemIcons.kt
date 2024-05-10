package com.example.composecampgroup4.presentation.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.outlined.FileCopy
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.example.composecampgroup4.R
import com.example.composecampgroup4.domain.entity.Jar

@Composable
fun JarItemIcons(
    modifier: Modifier = Modifier,
    jar: Jar,
    onFavoriteClick: (Jar) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End
    ) {

        // FavouriteIcon
        Icon(
            imageVector = if (jar.isFavourite) Icons.Default.Star else Icons.Default.StarOutline,
            contentDescription = "favourite Jar",
            modifier = Modifier
                .clickable(
                    interactionSource = interactionSource, indication = null,
                    onClick = { onFavoriteClick(jar) }
                )
        )

        // CopyIcon
        Icon(
            imageVector = Icons.Outlined.FileCopy,
            contentDescription = "copy Jar",
            modifier = Modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        clipboardManager.setText(
                            AnnotatedString(context.getString(R.string.jar_link, jar.jarId))
                        )
                    }
                )
                .padding(top = 4.dp)
                .size(22.dp)
        )
    }
}
