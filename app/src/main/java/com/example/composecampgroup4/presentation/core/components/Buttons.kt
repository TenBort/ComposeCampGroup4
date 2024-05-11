package com.example.composecampgroup4.presentation.core.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.composecampgroup4.R

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        )
    ) {
        Text(text = text)
    }
}

@Composable
fun DonateButton(
    modifier: Modifier = Modifier,
    jarId: String,
    text: String = stringResource(id = R.string.jar_donate),
    isEnabled: Boolean = true
) {
    val context = LocalContext.current
    val localUriHandler = LocalUriHandler.current

    PrimaryButton(
        modifier = modifier,
        text = text,
        onClick = { localUriHandler.openUri(context.getString(R.string.jar_link, jarId)) },
        isEnabled = isEnabled
    )
}

@Composable
fun DonateButtonWithCopyIcon(
    modifier: Modifier = Modifier,
    jarId: String,
    buttonHeight: Dp = 50.dp,
    text: String = stringResource(id = R.string.jar_donate),
    isEnabled: Boolean = true
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        DonateButton(
            modifier = Modifier.height(buttonHeight).weight(1f),
            jarId = jarId,
            text = text,
            isEnabled = isEnabled
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = {
                clipboardManager.setText(
                    AnnotatedString(context.getString(R.string.jar_link, jarId))
                )
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.FileCopy,
                contentDescription = stringResource(R.string.copy_jar_link)
            )
        }
    }
}