package com.example.lab5

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.lab5.data.FlightsDatabase
import com.example.lab5.data.UserPreferencesRepository

private const val INPUT_VALUE_PREFERENCE_NAME = "input_value"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = INPUT_VALUE_PREFERENCE_NAME
)

class FlightsApplication : Application() {
    lateinit var userPreferencesRepository: UserPreferencesRepository
    val database: FlightsDatabase by lazy { FlightsDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}