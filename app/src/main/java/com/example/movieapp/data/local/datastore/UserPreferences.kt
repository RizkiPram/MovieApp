package com.example.movieapp.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    fun getState(): Flow<Boolean>{
        return dataStore.data.map {
            it[STATE_KEY] ?: false
        }
    }
    suspend fun saveState(state:Boolean){
        dataStore.edit {
            it[STATE_KEY] = state
        }
    }
    suspend fun logout() {
        dataStore.edit {
            it[STATE_KEY] = false
        }
    }
    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}