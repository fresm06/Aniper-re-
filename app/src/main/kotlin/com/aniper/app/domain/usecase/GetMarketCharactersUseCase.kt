package com.aniper.app.domain.usecase

import com.aniper.app.data.repository.MarketRepository
import com.aniper.app.domain.model.MarketCharacter
import javax.inject.Inject

class GetMarketCharactersUseCase @Inject constructor(
    private val marketRepository: MarketRepository
) {
    suspend operator fun invoke(): List<MarketCharacter> {
        return marketRepository.getAllCharacters()
    }

    suspend fun search(query: String, tags: List<String> = emptyList()): List<MarketCharacter> {
        return marketRepository.searchCharacters(query, tags)
    }

    suspend fun getTrending(): List<MarketCharacter> {
        return marketRepository.getTrendingCharacters()
    }

    suspend fun getNew(): List<MarketCharacter> {
        return marketRepository.getNewCharacters()
    }
}
