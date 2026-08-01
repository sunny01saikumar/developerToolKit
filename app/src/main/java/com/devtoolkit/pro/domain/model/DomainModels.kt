package com.devtoolkit.pro.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Tool(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val route: String,
    val iconName: String,
    val gradients: List<String>
)

@Serializable
data class Note(
    val id: String,
    val title: String,
    val content: String,
    val timestamp: Long
)

@Serializable
data class HistoryItem(
    val toolId: String,
    val timestamp: Long
)

@Serializable
data class CommandItem(
    val command: String,
    val description: String,
    val example: String,
    val category: String
)

@Serializable
data class HttpStatusItem(
    val code: Int,
    val name: String,
    val category: String,
    val description: String,
    val example: String
)

@Serializable
data class HttpHeaderItem(
    val name: String,
    val description: String,
    val example: String,
    val category: String
)

data class JwtResult(
    val headerJson: String,
    val payloadJson: String,
    val isExpired: Boolean,
    val expiryDate: String,
    val signatureAlgorithm: String
)
