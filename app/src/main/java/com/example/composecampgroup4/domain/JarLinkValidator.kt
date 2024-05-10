package com.example.composecampgroup4.domain

import androidx.compose.runtime.Stable
import com.example.composecampgroup4.domain.entity.Result
import com.example.composecampgroup4.domain.entity.errors.LinkError

@Stable
class JarLinkValidator {

    fun validateLink(link: String): Result<String, LinkError> {
        if (link.isBlank()) return Result.Error(LinkError.EMPTY_LINK)

        val linkParts = link.split('/')


        return if (linkParts.size > 1&&link.contains("jar")) {
            val jarIdIndex = linkParts.indexOf("jar") + 1
            val jarId = linkParts[jarIdIndex]
            val questionMarkIndex = jarId.indexOf("?")

            if (questionMarkIndex != -1) {
                Result.Success(jarId.substring(0, questionMarkIndex))
            } else {
                Result.Success(jarId)
            }
        } else {
            Result.Error(LinkError.WRONG_LINK)
        }
    }
}