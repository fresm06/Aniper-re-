package com.aniper.app.data.repository

import com.aniper.app.data.remote.api.MarketApiService
import com.aniper.app.data.remote.dto.SubmitCharacterDto
import com.aniper.app.domain.model.MarketCharacter
import com.aniper.app.domain.model.Motion

class MarketRepository(
    private val apiService: MarketApiService
) {

    suspend fun getAllCharacters(): List<MarketCharacter> {
        return try {
            apiService.getAllCharacters().characters.map { it.toDomainModel() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun searchCharacters(query: String, tags: List<String> = emptyList()): List<MarketCharacter> {
        return try {
            val tagsString = tags.joinToString(",")
            apiService.searchCharacters(query, tagsString).characters.map { it.toDomainModel() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getTrendingCharacters(): List<MarketCharacter> {
        return try {
            apiService.getTrendingCharacters().characters.map { it.toDomainModel() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getNewCharacters(): List<MarketCharacter> {
        return try {
            apiService.getNewCharacters().characters.map { it.toDomainModel() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun submitCharacter(
        name: String,
        description: String,
        tags: List<String>,
        author: String,
        motionImages: Map<Motion, String>
    ): Result<MarketCharacter> {
        return try {
            val dto = SubmitCharacterDto(
                name = name,
                description = description,
                tags = tags,
                author = author,
                idleImage = motionImages[Motion.IDLE] ?: "",
                walkLeft = motionImages[Motion.WALK_LEFT] ?: "",
                walkRight = motionImages[Motion.WALK_RIGHT] ?: "",
                click = motionImages[Motion.CLICK] ?: "",
                grab = motionImages[Motion.GRAB] ?: "",
                fall = motionImages[Motion.FALL] ?: "",
                land = motionImages[Motion.LAND] ?: ""
            )
            val result = apiService.submitCharacter(dto)
            Result.success(result.toDomainModel())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun com.aniper.app.data.remote.dto.CharacterDto.toDomainModel(): MarketCharacter {
        return MarketCharacter(
            id = id,
            name = name,
            description = description,
            author = author,
            isOfficial = isOfficial,
            tags = tags,
            motions = mapOf(
                Motion.IDLE to idleImage,
                Motion.WALK_LEFT to walkLeft,
                Motion.WALK_RIGHT to walkRight,
                Motion.CLICK to click,
                Motion.GRAB to grab,
                Motion.FALL to fall,
                Motion.LAND to land
            ),
            downloadCount = downloadCount,
            isApproved = isApproved,
            createdAt = createdAt
        )
    }
}
