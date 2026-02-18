package com.aniper.app.domain.usecase

import com.aniper.app.data.repository.CharacterRepository
import com.aniper.app.domain.model.Character
import com.aniper.app.domain.model.CharacterStatus
import com.aniper.app.domain.model.MarketCharacter
import javax.inject.Inject

class DownloadCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(marketCharacter: MarketCharacter): Result<Character> {
        return try {
            val character = Character(
                id = marketCharacter.id,
                name = marketCharacter.name,
                description = marketCharacter.description,
                author = marketCharacter.author,
                isOfficial = marketCharacter.isOfficial,
                tags = marketCharacter.tags,
                motions = marketCharacter.motions,
                downloadCount = marketCharacter.downloadCount,
                isApproved = marketCharacter.isApproved,
                createdAt = marketCharacter.createdAt,
                status = CharacterStatus.INSTALLED
            )
            characterRepository.saveCharacter(character)
            Result.success(character)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
