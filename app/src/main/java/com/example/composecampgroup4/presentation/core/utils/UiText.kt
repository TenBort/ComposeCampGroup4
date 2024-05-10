package com.example.composecampgroup4.presentation.core.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

sealed class UiText {
    data class DynamicString(val value: String) : UiText()

    class StringResource(
        @StringRes val id: Int,
        vararg val args: Any
    ) : UiText()

    @Composable
    fun asString(): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> LocalContext.current.getString(id, *args)
        }
    }

    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> context.getString(id, *args)
        }
    }
}