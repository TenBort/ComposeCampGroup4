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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecampgroup4.R
import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.presentation.core.components.MoneyJarInfo
import com.example.composecampgroup4.presentation.core.components.PrimaryButton

@Composable
fun JarItemExpandableSection(
    modifier: Modifier = Modifier,
    jar: Jar,
    isClosed: Boolean,
    onButtonClick: () -> Unit
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Column {

        // ExpandTextButton
        Text(
            modifier = modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { isExpanded = !isExpanded }
                )
                .fillMaxWidth(),
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
                    modifier = Modifier.padding(vertical = 16.dp),
                    jar = jar,
                    fontSize = 12.sp,
                    iconSize = 20.dp
                )

                PrimaryButton(
                    text = stringResource(id = R.string.jar_donate),
                    isEnabled = !jar.closed,
                    onClick = onButtonClick
                )
            }
        }
    }
}