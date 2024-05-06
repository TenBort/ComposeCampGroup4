package com.example.composecampgroup4.domain.repository

import com.example.composecampgroup4.domain.entity.Jar
import kotlinx.coroutines.flow.Flow

interface JarDatabaseRepository {

    fun getAllJars(): Flow<List<Jar>>

    suspend fun getJar(jarId: String): Jar

    suspend fun upsertJar(jar: Jar)

    suspend fun deleteJar(jarId: String)

    suspend fun searchJar(searchRequest: String): List<Jar>
}