package com.example.composecampgroup4.presentation.screens.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
import com.example.composecampgroup4.R


@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var isSwiping by remember { mutableStateOf(false) }

    var isRemoved by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = showDialog) {
        if (showDialog){
            delay(animationDuration.toLong())
            showDialog = true
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount
                        isSwiping = offsetX < -10
                    }
                },

            ) {
            DeleteBackground(isSwiping)

            content(item)

            if (offsetX < -150 || offsetX > 150) {
                LaunchedEffect(Unit) {
                    showDialog = true
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = stringResource(id = R.string.jar_delete_title)) },
            text = { Text(text = stringResource(id = R.string.jar_delete_warning)) },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        isRemoved = true
                        onDelete(item)
                    }
                ) {
                    Text(text = stringResource(id = R.string.jar_delete_confirm))
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = false
                        isRemoved = false
                        offsetX = 0f
                    }
                ) {
                    Text(text = stringResource(id = R.string.jar_delete_cancel))
                }
            }
        )
    }
}

@Composable
fun DeleteBackground(
    isSwiping:Boolean
) {
    val color = if (isSwiping) { MaterialTheme.colorScheme.errorContainer } else Color.Transparent
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color, shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.White
        )
    }
}