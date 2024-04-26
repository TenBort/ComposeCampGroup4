package com.example.composecampgroup4.domain.entity

data class Jar (
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
    val userComment: String = ""
)