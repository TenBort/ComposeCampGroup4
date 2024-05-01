package com.example.composecampgroup4.domain

import androidx.compose.runtime.Stable
import com.example.composecampgroup4.domain.entity.Result
import com.example.composecampgroup4.domain.entity.errors.JarDataError

@Stable
class JarLinkValidator {

    fun validateLink(link: String): Result<String, JarDataError> {
        val linkParts = link.split('/')
        return if (linkParts.isNotEmpty()) {
            Result.Success(linkParts.last())
        } else {
            Result.Error(JarDataError.WrongId)
        }
    }
}