package com.example.composecampgroup4.domain.entity

data class Jar (
    val jarId: String = "",
    val longJarId: String = "",
    val amount: Long = 0L,
    val goal: Long = 0L,
    val ownerIcon: String = "",
    val title: String = "",
    val ownerName: String = "",
    val currency: Int = 0,
    val description: String = "",
    val closed: Boolean = false,
    val userComment: String = "",
    val isFavourite: Boolean = false
)