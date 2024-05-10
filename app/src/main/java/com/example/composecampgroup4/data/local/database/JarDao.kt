package com.example.composecampgroup4.data.local.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.composecampgroup4.data.local.model.JarDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface JarDao {

    @Query("SELECT * FROM jars ORDER BY isFavourite DESC, title ASC")
    fun getJars(): Flow<List<JarDbModel>>

    @Query("SELECT * FROM jars WHERE jarId=:jarId")
    fun getJar(jarId: String): Flow<JarDbModel>

    @Upsert
    suspend fun upsertJar(jarDbModel: JarDbModel)

    @Query("DELETE FROM jars WHERE jarId=:jarId")
    suspend fun deleteJar(jarId: String)

    @Query("SELECT * FROM jars WHERE ownerName LIKE '%' || :searchRequest || '%' OR title LIKE '%' || :searchRequest || '%' ORDER BY isFavourite DESC")
    suspend fun searchJar(searchRequest: String): List<JarDbModel>
}