package com.example.composecampgroup4.domain.entity

data class Jar (
    val id: Int,
    val amount: Long,
    val goal: Long,
    val title: String,
    val description: String,
    val ownerName: String,
    val ownerIcon: String,
    val closed: Boolean
)