package com.aniper.app.domain.usecase

import com.aniper.app.data.repository.CharacterRepository
import com.aniper.app.domain.model.Character
import com.aniper.app.domain.model.CharacterStatus
import com.aniper.app.domain.model.Motion
import javax.inject.Inject

class CreateCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(
        name: String,
        description: String,
        tags: List<String>,
        author: String,
        motions: Map<Motion, String>
    ): Result<Character> {
        return try {
            val character = Character(
                id = "user_${System.currentTimeMillis()}",
                name = name,
                description = description,
                author = author,
                isOfficial = false,
                tags = tags,
                motions = motions,
                downloadCount = 0,
                isApproved = false,
                createdAt = System.currentTimeMillis(),
                status = CharacterStatus.INSTALLED
            )
            characterRepository.saveCharacter(character)
            Result.success(character)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
