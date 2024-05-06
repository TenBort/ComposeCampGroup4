package com.example.composecampgroup4.data.repository

import com.example.composecampgroup4.data.local.database.JarDao
import com.example.composecampgroup4.data.mapper.toDbModel
import com.example.composecampgroup4.data.mapper.toEntities
import com.example.composecampgroup4.data.mapper.toEntity
import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.domain.repository.JarDatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class JarDatabaseRepositoryImpl @Inject constructor(
    private val jarDao: JarDao
) : JarDatabaseRepository {

    override fun getAllJars(): Flow<List<Jar>> = jarDao.getJars().map { it.toEntities() }

    override suspend fun getJar(jarId: String): Jar = jarDao.getJar(jarId).toEntity()

    override suspend fun upsertJar(jar: Jar) = jarDao.upsertJar(jar.toDbModel())

    override suspend fun deleteJar(jarId: String) = jarDao.deleteJar(jarId)

    override suspend fun searchJar(searchRequest: String): List<Jar> =
        jarDao.searchJar(searchRequest).toEntities()
}