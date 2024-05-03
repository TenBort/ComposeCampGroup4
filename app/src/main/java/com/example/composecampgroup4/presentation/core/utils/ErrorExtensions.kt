package com.example.composecampgroup4.presentation.core.utils

import com.example.composecampgroup4.R
import com.example.composecampgroup4.domain.entity.errors.JarDataError
import com.example.composecampgroup4.domain.entity.errors.LinkError

fun JarDataError.asUiText(): UiText {
    return when(this) {
        JarDataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(R.string.the_request_timed_out)
        JarDataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(R.string.youve_hit_your_rate_limit)
        JarDataError.Network.NO_INTERNET -> UiText.StringResource(R.string.no_internet)
        JarDataError.Network.SERVER_ERROR -> UiText.StringResource(R.string.server_error)
        JarDataError.Network.UNKNOWN -> UiText.StringResource(R.string.unknown_error)
        JarDataError.WrongId -> UiText.StringResource(R.string.wrong_id_error)
        is JarDataError.Unknown -> UiText.DynamicString(this.message)
    }
}

fun LinkError.asUiText(): UiText {
    return when(this) {
        LinkError.WRONG_LINK -> UiText.StringResource(R.string.wrong_id_error)
        LinkError.EMPTY_LINK -> UiText.StringResource(R.string.empty_link_error)
    }
}