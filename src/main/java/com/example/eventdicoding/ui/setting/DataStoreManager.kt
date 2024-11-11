package com.example.eventdicoding.ui.setting

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreManager(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        val THEME_KEY = booleanPreferencesKey("dark_mode")
    }

    suspend fun setTheme(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkMode
        }
    }

    val themeFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[THEME_KEY] ?: false
        }

}