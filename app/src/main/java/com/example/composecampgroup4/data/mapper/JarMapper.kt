package com.example.composecampgroup4.data.mapper

import com.example.composecampgroup4.data.network.dto.JarDto
import com.example.composecampgroup4.domain.entity.Jar

fun JarDto.toEntity(ownerName: String, longJarId: String) = Jar(
    jarId = jarId,
    longJarId = longJarId,
    amount = amount.toLong(),
    goal = goal.toLong(),
    ownerIcon = ownerIcon,
    title = title,
    ownerName = ownerName,
    currency = currency,
    description = description,
    closed = closed
)