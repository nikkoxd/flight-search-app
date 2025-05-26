package com.example.lab5.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val INPUT_VALUE = stringPreferencesKey("input_value")
    }

    val inputValue: Flow<String> = dataStore.data.map { preferences ->
        preferences[INPUT_VALUE] ?: ""
    }

    suspend fun saveInputValuePreference(inputValue: String) {
        dataStore.edit { preferences ->
            preferences[INPUT_VALUE] = inputValue
        }
    }
}