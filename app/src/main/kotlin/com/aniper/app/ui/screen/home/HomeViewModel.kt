package com.aniper.app.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aniper.app.data.repository.CharacterRepository
import com.aniper.app.domain.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    val characters: Flow<List<Character>> = characterRepository.getAllCharacters()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val activeCharacterId: Flow<String?> = characterRepository.getActiveCharacterId()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun setActiveCharacter(characterId: String?) {
        viewModelScope.launch {
            characterRepository.setActiveCharacter(characterId)
        }
    }

    fun deleteCharacter(characterId: String) {
        viewModelScope.launch {
            characterRepository.deleteCharacter(characterId)
        }
    }
}
