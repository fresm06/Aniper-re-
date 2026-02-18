package com.aniper.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(
    val id: String,
    val name: String,
    val description: String,
    val author: String,
    @SerialName("is_official")
    val isOfficial: Boolean = false,
    val tags: List<String> = emptyList(),
    @SerialName("idle_image")
    val idleImage: String,
    @SerialName("walk_left")
    val walkLeft: String,
    @SerialName("walk_right")
    val walkRight: String,
    val click: String,
    val grab: String,
    val fall: String,
    val land: String,
    @SerialName("download_count")
    val downloadCount: Int = 0,
    @SerialName("is_approved")
    val isApproved: Boolean = false,
    @SerialName("created_at")
    val createdAt: Long = System.currentTimeMillis()
)

@Serializable
data class SubmitCharacterDto(
    val name: String,
    val description: String,
    val tags: List<String>,
    val author: String = "Unknown",
    @SerialName("idle_image")
    val idleImage: String,
    @SerialName("walk_left")
    val walkLeft: String,
    @SerialName("walk_right")
    val walkRight: String,
    val click: String,
    val grab: String,
    val fall: String,
    val land: String
)

@Serializable
data class CharacterListDto(
    val characters: List<CharacterDto>,
    val total: Int
)
