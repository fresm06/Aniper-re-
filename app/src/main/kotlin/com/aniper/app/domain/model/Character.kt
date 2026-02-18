package com.aniper.app.domain.model

data class Character(
    val id: String,
    val name: String,
    val description: String,
    val author: String,
    val isOfficial: Boolean = false,
    val tags: List<String> = emptyList(),
    val motions: Map<Motion, String>, // URL or file path to motion images
    val downloadCount: Int = 0,
    val isApproved: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val status: CharacterStatus = CharacterStatus.INSTALLED
)

enum class CharacterStatus {
    NOT_INSTALLED,
    INSTALLED,
    ACTIVE
}
