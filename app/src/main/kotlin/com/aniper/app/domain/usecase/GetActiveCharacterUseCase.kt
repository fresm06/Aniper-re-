package com.aniper.app.domain.usecase

import com.aniper.app.data.repository.CharacterRepository
import com.aniper.app.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetActiveCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(): Flow<Character?> {
        return characterRepository.getActiveCharacterId()
            .flatMapLatest { characterId ->
                if (characterId != null) {
                    kotlinx.coroutines.flow.flow {
                        val character = characterRepository.getCharacterById(characterId)
                        emit(character)
                    }
                } else {
                    flowOf(null)
                }
            }
    }
}
