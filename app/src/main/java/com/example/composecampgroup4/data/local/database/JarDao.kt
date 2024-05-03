package com.example.composecampgroup4.data.local.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.composecampgroup4.data.local.model.JarDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface JarDao {

    @Query("SELECT * FROM jars")
    fun getJars(): Flow<List<JarDbModel>>

    @Query("SELECT * FROM jars WHERE jarId=:jarId")
    fun getJar(jarId: String): JarDbModel

    @Upsert
    suspend fun upsertJar(jarDbModel: JarDbModel)

    @Query("DELETE FROM jars WHERE jarId=:jarId")
    suspend fun deleteJar(jarId: String)
}