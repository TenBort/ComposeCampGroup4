package com.example.composecampgroup4.presentation.screens.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecampgroup4.R
import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.presentation.core.components.DonateButtonWithCopyIcon
import com.example.composecampgroup4.presentation.core.components.MoneyJarInfo

@Composable
fun JarItemExpandableSection(
    modifier: Modifier = Modifier,
    jar: Jar,
    isClosed: Boolean,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier) {

        // ExpandTextButton
        Text(
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { isExpanded = !isExpanded }
                ),
            text = if (isExpanded) stringResource(id = R.string.hide_details) else stringResource(id = R.string.show_details),
            fontSize = 14.sp,
            color = if (isClosed) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.primary
        )

        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = isExpanded,
            enter = fadeIn() + expandVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
            exit = fadeOut() + shrinkVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        ) {
            Column(modifier) {
                MoneyJarInfo(
                    modifier = Modifier.padding(top = 16.dp, bottom = 20.dp),
                    jar = jar,
                    fontSize = 12.sp,
                    iconSize = 20.dp
                )

                if (jar.userComment.isNotBlank()) {
                    Text(
                        text = stringResource(R.string.comment, jar.userComment),
                        modifier = Modifier.padding(bottom = 16.dp),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.outline,
                        lineHeight = 18.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                DonateButtonWithCopyIcon(
                    modifier = Modifier.fillMaxWidth(),
                    jarId = jar.jarId,
                    buttonHeight = 44.dp,
                    isEnabled = !jar.closed
                )
            }
        }
    }
}