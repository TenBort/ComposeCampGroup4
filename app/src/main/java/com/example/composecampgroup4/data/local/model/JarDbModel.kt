package com.example.composecampgroup4.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jars")
data class JarDbModel(
    @PrimaryKey
    val jarId: String,
    val longJarId: String,
    val amount: Long,
    val goal: Long,
    val ownerIcon: String,
    val title: String,
    val ownerName: String,
    val currency: Int,
    val description: String,
    val closed: Boolean,
    val userComment: String,
    val isFavourite: Boolean
)