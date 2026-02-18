package com.aniper.app.ui.screen.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aniper.app.data.repository.CharacterRepository
import com.aniper.app.domain.model.Motion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _tags = MutableStateFlow<List<String>>(emptyList())
    val tags: StateFlow<List<String>> = _tags.asStateFlow()

    private val _motionImages = MutableStateFlow<Map<Motion, String>>(emptyMap())
    val motionImages: StateFlow<Map<Motion, String>> = _motionImages.asStateFlow()

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    fun setName(name: String) {
        _name.value = name.take(30)
    }

    fun setDescription(description: String) {
        _description.value = description.take(200)
    }

    fun addTag(tag: String) {
        if (_tags.value.size < 3 && tag.isNotBlank()) {
            _tags.value = _tags.value + tag.take(15)
        }
    }

    fun removeTag(tag: String) {
        _tags.value = _tags.value.filter { it != tag }
    }

    fun setMotionImage(motion: Motion, imagePath: String) {
        _motionImages.value = _motionImages.value + (motion to imagePath)
    }

    fun saveCharacter() {
        if (!isValid()) {
            _message.value = "모든 필드를 입력해주세요 (7개 모션 이미지 필수)"
            return
        }

        viewModelScope.launch {
            _isSaving.value = true
            characterRepository.saveCharacter(
                com.aniper.app.domain.model.Character(
                    id = "user_${System.currentTimeMillis()}",
                    name = _name.value,
                    description = _description.value,
                    author = "사용자",
                    isOfficial = false,
                    tags = _tags.value,
                    motions = _motionImages.value,
                    downloadCount = 0,
                    isApproved = false,
                    status = com.aniper.app.domain.model.CharacterStatus.INSTALLED
                )
            )
            _message.value = "캐릭터가 저장되었습니다"
            _isSaving.value = false
        }
    }

    private fun isValid(): Boolean {
        return _name.value.isNotBlank() &&
            _description.value.isNotBlank() &&
            _tags.value.size == 3 &&
            Motion.values().all { _motionImages.value.containsKey(it) }
    }

    fun canSave(): Boolean = isValid()
}
