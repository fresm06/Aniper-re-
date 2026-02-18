package com.aniper.app.domain.model

data class MarketCharacter(
    val id: String,
    val name: String,
    val description: String,
    val author: String,
    val isOfficial: Boolean = false,
    val tags: List<String> = emptyList(),
    val motions: Map<Motion, String>,
    val downloadCount: Int = 0,
    val isApproved: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

fun MarketCharacter.toCharacter(): Character = Character(
    id = id,
    name = name,
    description = description,
    author = author,
    isOfficial = isOfficial,
    tags = tags,
    motions = motions,
    downloadCount = downloadCount,
    isApproved = isApproved,
    createdAt = createdAt,
    status = CharacterStatus.NOT_INSTALLED
)
