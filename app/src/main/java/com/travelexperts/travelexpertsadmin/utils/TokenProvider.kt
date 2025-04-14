package com.travelexperts.travelexpertsadmin.utils

import android.content.Context
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

object TokenProvider {
    private val TOKEN_KEY = stringPreferencesKey("bearer_token")

    fun getToken(context: Context): String {
        return try {
            runBlocking {
                context.dataStore.data.first()[TOKEN_KEY] ?: ""
            }
        } catch (e: Exception) {
            ""
        }
    }
}
