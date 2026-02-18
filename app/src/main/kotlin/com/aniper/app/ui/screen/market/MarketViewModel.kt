package com.aniper.app.ui.screen.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aniper.app.data.repository.CharacterRepository
import com.aniper.app.data.repository.MarketRepository
import com.aniper.app.domain.model.MarketCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val marketRepository: MarketRepository,
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _marketCharacters = MutableStateFlow<List<MarketCharacter>>(emptyList())
    val marketCharacters: StateFlow<List<MarketCharacter>> = _marketCharacters.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    init {
        loadMarketCharacters()
    }

    private fun loadMarketCharacters() {
        viewModelScope.launch {
            _isLoading.value = true
            val characters = marketRepository.getAllCharacters()
            _marketCharacters.value = characters
            _isLoading.value = false
        }
    }

    fun search(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            _isLoading.value = true
            val characters = if (query.isBlank()) {
                marketRepository.getAllCharacters()
            } else {
                marketRepository.searchCharacters(query)
            }
            _marketCharacters.value = characters
            _isLoading.value = false
        }
    }

    fun downloadCharacter(character: MarketCharacter) {
        viewModelScope.launch {
            val result = characterRepository.insertCharacters(listOf(character.toCharacter()))
            _message.value = "다운로드 완료: ${character.name}"
        }
    }

    private fun MarketCharacter.toCharacter(): com.aniper.app.domain.model.Character {
        return com.aniper.app.domain.model.Character(
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
            status = com.aniper.app.domain.model.CharacterStatus.INSTALLED
        )
    }
}
