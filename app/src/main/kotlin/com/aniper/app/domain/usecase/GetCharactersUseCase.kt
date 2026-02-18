package com.aniper.app.domain.usecase

import com.aniper.app.data.repository.CharacterRepository
import com.aniper.app.domain.model.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(): Flow<List<Character>> {
        return characterRepository.getAllCharacters()
    }
}
