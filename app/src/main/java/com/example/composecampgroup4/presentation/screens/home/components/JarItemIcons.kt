package com.example.composecampgroup4.presentation.screens.home.components

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.composecampgroup4.R
import com.example.composecampgroup4.domain.entity.Jar

@Composable
fun JarItemIcons(
    modifier: Modifier = Modifier,
    jar: Jar,
    onFavoriteClick: (Jar) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, context.getString(R.string.jar_link, jar.jarId))
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End
    ) {

        // FavouriteIcon
        Icon(
            imageVector = if (jar.isFavourite) Icons.Default.Star else Icons.Default.StarOutline,
            contentDescription = stringResource(R.string.add_favourite_jar),
            modifier = Modifier
                .clickable(
                    interactionSource = interactionSource, indication = null,
                    onClick = { onFavoriteClick(jar) }
                )
        )

        Spacer(modifier = Modifier.height(6.dp))

        // ShareIcon
        Icon(
            imageVector = Icons.Outlined.Share,
            contentDescription = stringResource(R.string.share_jar),
            modifier = Modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { startActivity(context, shareIntent, null) }
                )
                .size(22.dp)
        )
    }
}
