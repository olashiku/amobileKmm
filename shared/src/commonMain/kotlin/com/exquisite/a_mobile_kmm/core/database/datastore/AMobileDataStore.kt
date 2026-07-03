package com.exquisite.a_mobile_kmm.core.database.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer


class AMobileDataStore(private val dataStore: DataStore<Preferences>) {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }

    private val mapSerializer = MapSerializer(String.serializer(), String.serializer())

    private companion object {
        val ID_KEY = stringPreferencesKey("id")
        val FIRST_NAME = stringPreferencesKey("first_name")
        val LAST_NAME = stringPreferencesKey("last_name")
        val EMAIL_KEY = stringPreferencesKey("email")
        val PHONE_KEY = stringPreferencesKey("phone")
        val TOKEN_KEY = stringPreferencesKey("token")
        val PROFILE_PICTURE = stringPreferencesKey("profile_picture")
        val HAS_LOGGED_IN = booleanPreferencesKey("has_logged_in")
        val REMEMBER_ME = booleanPreferencesKey("remember_me")

    }

    suspend fun saveUserProfile(
        id: String, firstName: String,  lastName: String,email: String, phone: String,
        profilePicture: String, token: String
    ) {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = id
            preferences[FIRST_NAME] = firstName
            preferences[LAST_NAME] = lastName
            preferences[EMAIL_KEY] = email
            preferences[PHONE_KEY] = phone
            preferences[TOKEN_KEY] = token
            preferences[PROFILE_PICTURE] = profilePicture
        }
    }

    suspend fun saveRememberMe(rememberMe:Boolean){
       dataStore.edit{pref ->
           pref[REMEMBER_ME] = rememberMe
       }
    }


    fun getRememberMe() =  dataStore.data.map { preferences ->
        preferences[REMEMBER_ME] ?: false
    }


    fun getAuthorization() =  dataStore.data.map { preferences ->
        preferences[TOKEN_KEY] ?: ""
    }


    fun getCustomerName() = dataStore.data.map { preferences ->
        "${preferences[FIRST_NAME] ?: ""} ${preferences[LAST_NAME] ?: ""}".trim()
    }

     fun getProfilePicture() = dataStore.data.map { preferences ->
         preferences[PROFILE_PICTURE] ?: ""
     }

    fun getUserId() =  dataStore.data.map { preferences ->
        preferences[ID_KEY] ?: ""
    }






    suspend fun saveHasLoggedIn(hasLoggedIn:Boolean?){
        dataStore.edit { preferences ->
            preferences[HAS_LOGGED_IN] = hasLoggedIn ?:false
        }
    }

    fun hasLoggedIn() = dataStore.data.map { preferences ->
        preferences[HAS_LOGGED_IN]
    }

       suspend fun clearAllData() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }


}