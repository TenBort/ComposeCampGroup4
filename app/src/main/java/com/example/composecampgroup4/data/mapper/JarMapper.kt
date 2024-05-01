package com.example.composecampgroup4.data.mapper

import com.example.composecampgroup4.data.local.model.JarDbModel
import com.example.composecampgroup4.data.network.dto.JarDto
import com.example.composecampgroup4.domain.entity.Jar

fun JarDto.toEntity(ownerName: String, longJarId: String) = Jar(
    jarId = jarId,
    longJarId = longJarId,
    amount = amount,
    goal = goal,
    ownerIcon = ownerIcon,
    title = title,
    ownerName = ownerName,
    currency = currency,
    description = description,
    closed = closed
)

fun Jar.toDbModel(): JarDbModel = JarDbModel(
    jarId = jarId,
    longJarId = longJarId,
    amount = amount,
    goal = goal,
    ownerIcon = ownerIcon,
    title = title,
    ownerName = ownerName,
    currency = currency,
    description = description,
    closed = closed,
    userComment = userComment
)

fun JarDbModel.toEntity(): Jar = Jar(
    jarId = jarId,
    longJarId = longJarId,
    amount = amount,
    goal = goal,
    ownerIcon = ownerIcon,
    title = title,
    ownerName = ownerName,
    currency = currency,
    description = description,
    closed = closed,
    userComment = userComment
)

fun List<JarDbModel>.toEntities(): List<Jar> = map { it.toEntity() }