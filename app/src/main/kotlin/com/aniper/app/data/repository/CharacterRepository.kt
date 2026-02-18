package com.aniper.app.data.repository

import com.aniper.app.data.local.db.dao.CharacterDao
import com.aniper.app.data.local.entity.CharacterEntity
import com.aniper.app.data.local.prefs.AppPreferences
import com.aniper.app.domain.model.Character
import com.aniper.app.domain.model.CharacterStatus
import com.aniper.app.domain.model.Motion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepository(
    private val characterDao: CharacterDao,
    private val appPreferences: AppPreferences
) {

    fun getAllCharacters(): Flow<List<Character>> {
        return characterDao.getAllCharacters().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    fun getCharactersByStatus(status: CharacterStatus): Flow<List<Character>> {
        return characterDao.getCharactersByStatus(status.name).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    fun getActiveCharacterId(): Flow<String?> = appPreferences.activeCharacterId

    suspend fun getCharacterById(id: String): Character? {
        return characterDao.getById(id)?.toDomainModel()
    }

    suspend fun saveCharacter(character: Character) {
        characterDao.insert(character.toEntity())
    }

    suspend fun updateCharacter(character: Character) {
        characterDao.update(character.toEntity())
    }

    suspend fun deleteCharacter(id: String) {
        characterDao.deleteById(id)
    }

    suspend fun setActiveCharacter(characterId: String?) {
        appPreferences.setActiveCharacterId(characterId)
    }

    suspend fun insertCharacters(characters: List<Character>) {
        val entities = characters.map { it.toEntity() }
        characterDao.insertAll(entities)
    }

    private fun CharacterEntity.toDomainModel(): Character {
        return Character(
            id = id,
            name = name,
            description = description,
            author = author,
            isOfficial = isOfficial,
            tags = tags,
            motions = motions.mapKeys { (motionName, _) ->
                Motion.valueOf(motionName)
            },
            downloadCount = downloadCount,
            isApproved = isApproved,
            createdAt = createdAt,
            status = CharacterStatus.valueOf(status)
        )
    }

    private fun Character.toEntity(): CharacterEntity {
        return CharacterEntity(
            id = id,
            name = name,
            description = description,
            author = author,
            isOfficial = isOfficial,
            tags = tags,
            motions = motions.mapKeys { (motion, _) -> motion.name },
            downloadCount = downloadCount,
            isApproved = isApproved,
            createdAt = createdAt,
            status = status.name
        )
    }
}
