package com.example.composecampgroup4.domain.entity

data class Jar (
    val id: Int,
    val name: String,
    val description: String,
    val collected: Double,
    val goal: Double,
)