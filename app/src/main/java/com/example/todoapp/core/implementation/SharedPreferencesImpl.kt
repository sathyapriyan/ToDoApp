package com.example.todoapp.core.implementation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.todoapp.data.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class SharedPreferencesImpl @Inject constructor(
    private val dataStorePreferences: DataStore<Preferences>
): SharedPreferences {

    override suspend fun putString(key: Preferences.Key<String>, value: String) {
        dataStorePreferences.edit { preferences ->
            preferences[key] = value
        }
    }

    override fun getString(key: Preferences.Key<String>): Flow<String?> {
        return dataStorePreferences.data.map { pref ->
            pref[key]
        }.catch { exception ->
            println(exception.cause)
        }
    }

    override suspend fun putInt(key: Preferences.Key<Int>, value: Int) {
        dataStorePreferences.edit { preferences ->
            preferences[key] = value
        }
    }

    override fun getInt(key: Preferences.Key<Int>): Flow<Int?> {
        return dataStorePreferences.data.map { pref ->
            pref[key]
        }.catch { exception ->
            println(exception.cause)
        }
    }

    override suspend fun putLong(key: Preferences.Key<Long>, value: Long) {
        dataStorePreferences.edit { preferences ->
            preferences[key] = value
        }
    }

    override fun getLong(key: Preferences.Key<Long>): Flow<Long?> {
        return dataStorePreferences.data.map { pref ->
            pref[key]
        }.catch { exception ->
            println(exception.cause)
        }
    }

    override suspend fun putFloat(key: Preferences.Key<Float>, value: Float) {
        dataStorePreferences.edit { preferences ->
            preferences[key] = value
        }
    }

    override fun getFloat(key: Preferences.Key<Float>): Flow<Float?> {
        return dataStorePreferences.data.map { pref ->
            pref[key]
        }.catch { exception ->
            println(exception.cause)
        }
    }

    override suspend fun putDouble(key: Preferences.Key<Double>, value: Double) {
        dataStorePreferences.edit { preferences ->
            preferences[key] = value
        }
    }

    override fun getDouble(key: Preferences.Key<Double>): Flow<Double?> {
        return dataStorePreferences.data.map { pref ->
            pref[key]
        }.catch { exception ->
            println(exception.cause)
        }
    }

    override suspend fun putBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStorePreferences.edit { preferences ->
            preferences[key] = value
        }
    }

    override fun getBoolean(key: Preferences.Key<Boolean>): Flow<Boolean?> {
        return dataStorePreferences.data.map { pref ->
            pref[key]
        }.catch { exception ->
            println(exception.cause)
        }
    }


}