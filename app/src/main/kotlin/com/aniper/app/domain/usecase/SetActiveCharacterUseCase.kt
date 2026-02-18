package com.aniper.app.domain.usecase

import com.aniper.app.data.repository.CharacterRepository
import javax.inject.Inject

class SetActiveCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(characterId: String?): Result<Unit> {
        return try {
            characterRepository.setActiveCharacter(characterId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
