package com.example.composecampgroup4.domain.repository

import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.domain.entity.Result
import com.example.composecampgroup4.domain.entity.errors.JarDataError

interface JarNetworkRepository {
    suspend fun loadJarData(jarId: String): Result<Jar, JarDataError>
}