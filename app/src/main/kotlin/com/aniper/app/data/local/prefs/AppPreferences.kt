package com.aniper.app.data.local.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val APP_PREFERENCES = "app_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = APP_PREFERENCES)

class AppPreferences(private val context: Context) {
    companion object {
        private val ACTIVE_CHARACTER_ID = stringPreferencesKey("active_character_id")
    }

    val activeCharacterId: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[ACTIVE_CHARACTER_ID]
        }

    suspend fun setActiveCharacterId(characterId: String?) {
        context.dataStore.edit { preferences ->
            if (characterId != null) {
                preferences[ACTIVE_CHARACTER_ID] = characterId
            } else {
                preferences.remove(ACTIVE_CHARACTER_ID)
            }
        }
    }
}
