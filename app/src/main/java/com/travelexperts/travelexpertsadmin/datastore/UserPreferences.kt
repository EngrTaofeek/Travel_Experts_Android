package com.travelexperts.travelexpertsadmin.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension property for Context
private val Context.dataStore by preferencesDataStore(name = "user_prefs")

object UserPreferences {

    // Keys
    private val EMAIL_KEY = stringPreferencesKey("email")
    private val USER_ID_KEY = stringPreferencesKey("user_id")
    private val TOKEN_KEY = stringPreferencesKey("bearer_token")
    private val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
    val FULL_NAME_KEY = stringPreferencesKey("full_name")
    val ROLE_KEY = stringPreferencesKey("role")
    val POSITION_KEY = stringPreferencesKey("position")
    val STATUS_KEY = stringPreferencesKey("status")
    val PHOTO_KEY = stringPreferencesKey("profile_image")

    suspend fun setFullName(context: Context, name: String) {
        context.dataStore.edit { it[FULL_NAME_KEY] = name }
    }

    suspend fun setRole(context: Context, role: String) {
        context.dataStore.edit { it[ROLE_KEY] = role }
    }

    suspend fun setPosition(context: Context, position: String) {
        context.dataStore.edit { it[POSITION_KEY] = position }
    }

    suspend fun setStatus(context: Context, status: String) {
        context.dataStore.edit { it[STATUS_KEY] = status }
    }

    suspend fun setProfilePhoto(context: Context, url: String) {
        context.dataStore.edit { it[PHOTO_KEY] = url }
    }

    fun getFullName(context: Context): Flow<String?> = context.dataStore.data.map { it[FULL_NAME_KEY] }
    fun getRole(context: Context): Flow<String?> = context.dataStore.data.map { it[ROLE_KEY] }
    fun getPosition(context: Context): Flow<String?> = context.dataStore.data.map { it[POSITION_KEY] }
    fun getStatus(context: Context): Flow<String?> = context.dataStore.data.map { it[STATUS_KEY] }
    fun getProfilePhoto(context: Context): Flow<String?> = context.dataStore.data.map { it[PHOTO_KEY] }


    // ✅ SAVE Individual Fields
    suspend fun setEmail(context: Context, email: String) {
        context.dataStore.edit { it[EMAIL_KEY] = email }
    }

    suspend fun setUserId(context: Context, userId: String) {
        context.dataStore.edit { it[USER_ID_KEY] = userId }
    }

    suspend fun setBearerToken(context: Context, token: String) {
        context.dataStore.edit { it[TOKEN_KEY] = token }
    }

    suspend fun setLoggedIn(context: Context, isLoggedIn: Boolean) {
        context.dataStore.edit { it[IS_LOGGED_IN_KEY] = isLoggedIn }
    }

    //  READ Individual Fields
    fun getEmail(context: Context): Flow<String?> =
        context.dataStore.data.map { it[EMAIL_KEY] }

    fun getUserId(context: Context): Flow<String?> =
        context.dataStore.data.map { it[USER_ID_KEY] }

    fun getBearerToken(context: Context): Flow<String?> =
        context.dataStore.data.map { it[TOKEN_KEY] }

    fun isLoggedIn(context: Context): Flow<Boolean> =
        context.dataStore.data.map { it[IS_LOGGED_IN_KEY] ?: false }

    //  CLEAR ALL
    suspend fun clearAll(context: Context) {
        context.dataStore.edit { it.clear() }
    }
}
