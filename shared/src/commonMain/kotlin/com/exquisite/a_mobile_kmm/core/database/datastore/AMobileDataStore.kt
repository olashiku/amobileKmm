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
        val FULL_NAME_KEY = stringPreferencesKey("full_name")
        val EMAIL_KEY = stringPreferencesKey("email")
        val PHONE_KEY = stringPreferencesKey("phone")
        val TOKEN_KEY = stringPreferencesKey("token")
        val PROFILE_PICTURE = stringPreferencesKey("profile_picture")
        val FRONT_PICTURE = byteArrayPreferencesKey("front_picture")
        val BACK_PICTURE = byteArrayPreferencesKey("back_picture")
        val LEFT_PICTURE = byteArrayPreferencesKey("left_picture")
        val RIGHT_PICTURE = byteArrayPreferencesKey("right_picture")
        val FRONT_PICTURE_URL = stringPreferencesKey("front_picture_url")
        val BACK_PICTURE_URL = stringPreferencesKey("back_picture_url")
        val LEFT_PICTURE_URL = stringPreferencesKey("left_picture_url")
        val RIGHT_PICTURE_URL = stringPreferencesKey("right_picture_url")
        val REFERENCE_PICTURE_URL = stringPreferencesKey("reference_picture_url")
        val SIZE_CATEGORY = stringPreferencesKey("size_category")
        val MEASUREMENT_MEN = stringPreferencesKey("measurement_men")
        val MEASUREMENT_WOMEN = stringPreferencesKey("measurement_women")
        val MEASUREMENT_UNISEX = stringPreferencesKey("measurement_unisex")
        val HAS_LOGGED_IN = booleanPreferencesKey("has_logged_in")
        val REMEMBER_ME = booleanPreferencesKey("remember_me")

        // Guided measurement keys
        val PREFERRED_UNIT = stringPreferencesKey("measurement_preferred_unit")

        // Helper functions for measurement keys
        fun measurementValueKey(category: String, fieldKey: String) =
            stringPreferencesKey("meas_${category}_${fieldKey}_cm")

        fun measurementUnitKey(category: String, fieldKey: String) =
            stringPreferencesKey("meas_${category}_${fieldKey}_unit")
    }

    suspend fun saveUserProfile(
        id: String, fullName: String, email: String, phone: String,
        profilePicture: String, token: String
    ) {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = id
            preferences[FULL_NAME_KEY] = fullName
            preferences[EMAIL_KEY] = email
            preferences[PHONE_KEY] = phone
            preferences[TOKEN_KEY] = token
            preferences[PROFILE_PICTURE] = profilePicture
        }
    }

    suspend fun saveFrontImage(image: ByteArray) {
        dataStore.edit { preferences ->
            preferences[FRONT_PICTURE] = image
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

    suspend fun saveProfilePicture(profilePicture:String){
        dataStore.edit {  preferences ->
            preferences[PROFILE_PICTURE] = profilePicture
        }
    }

    suspend fun saveName(name:String){
        dataStore.edit {  preferences ->
            preferences[FULL_NAME_KEY] = name
        }
    }


    suspend fun savePhoneNo(phone:String){
        dataStore.edit {  preferences ->
            preferences[PHONE_KEY] = phone
        }
    }


    suspend fun saveMeasurements(measurement: Map<String, String>, category: String) {
        val normalizedCategory = category.lowercase()
        val key = when (normalizedCategory) {
            "men" -> MEASUREMENT_MEN
            "women" -> MEASUREMENT_WOMEN
            "unisex" -> MEASUREMENT_UNISEX
            else -> {
                println("⚠️ Unknown category: $category, defaulting to Men")
                MEASUREMENT_MEN
            }
        }
        println("💾 DataStore.saveMeasurements: Saving ${measurement.size} measurements for category: $category (normalized: $normalizedCategory)")
        println("   Key: ${key.name}")
        println("   Measurements to save:")
        measurement.forEach { (field, value) ->
            println("      - $field: $value")
        }

        dataStore.edit { preference ->
            val jsonString = json.encodeToString(mapSerializer, measurement)
            preference[key] = jsonString
            println("   Encoded JSON length: ${jsonString.length}")
        }

        // Verify it was saved by reading it back
        val verified = dataStore.data.first()[key]
        if (verified != null) {
            val decoded = json.decodeFromString(mapSerializer, verified)
            println("✅ DataStore: Successfully saved and verified ${decoded.size} measurements for $category")
        } else {
            println("❌ DataStore: FAILED to save - verification returned null!")
        }
    }

    fun getMeasurements(category: String) = dataStore.data.map { preferences ->
        val normalizedCategory = category.lowercase()
        val key = when (normalizedCategory) {
            "men" -> MEASUREMENT_MEN
            "women" -> MEASUREMENT_WOMEN
            "unisex" -> MEASUREMENT_UNISEX
            else -> {
                println("⚠️ Unknown category: $category, defaulting to Men")
                MEASUREMENT_MEN
            }
        }
        val jsonString = preferences[key] ?: "{}"
        try {
            val result = json.decodeFromString(mapSerializer, jsonString)
            println("📖 DataStore: Loaded ${result.size} measurements for category: $category (normalized: $normalizedCategory)")
            result
        } catch (e: Exception) {
            println("❌ DataStore: Error loading measurements for $category: ${e.message}")
            emptyMap()
        }
    }


    suspend fun saveCategory(category:String){
        println("💾 DataStore.saveCategory: Saving category: '$category'")
        dataStore.edit { preferences ->
            preferences[SIZE_CATEGORY] = category
        }
        // Verify
        val verified = dataStore.data.first()[SIZE_CATEGORY]
        println("✅ DataStore: Category saved and verified: '$verified'")
    }

    fun getCategory()=  dataStore.data.map { preferences ->
        preferences[SIZE_CATEGORY] ?: ""
    }


    suspend fun saveBackImage(image: ByteArray) {
        dataStore.edit { preferences ->
            preferences[BACK_PICTURE] = image
        }
    }

    suspend fun saveLeftImage(image: ByteArray) {
        dataStore.edit { preferences ->
            preferences[LEFT_PICTURE] = image
        }
    }

    suspend fun saveRightImage(image: ByteArray) {
        dataStore.edit { preferences ->
            preferences[RIGHT_PICTURE] = image
        }
    }

    fun getFrontImage()=  dataStore.data.map { preferences ->
        preferences[FRONT_PICTURE]
    }

    fun getBackImage() = dataStore.data.map { preferences ->
        preferences[BACK_PICTURE]
    }

    fun getLeftImage() = dataStore.data.map { preferences ->
        preferences[LEFT_PICTURE]
    }

    fun getRightImage() = dataStore.data.map { preferences ->
        preferences[RIGHT_PICTURE]
    }

    // Save Firebase URLs
    suspend fun saveFrontImageUrl(url: String) {
        dataStore.edit { preferences ->
            preferences[FRONT_PICTURE_URL] = url
        }
    }

    suspend fun saveBackImageUrl(url: String) {
        dataStore.edit { preferences ->
            preferences[BACK_PICTURE_URL] = url
        }
    }

    suspend fun saveLeftImageUrl(url: String) {
        dataStore.edit { preferences ->
            preferences[LEFT_PICTURE_URL] = url
        }
    }

    suspend fun saveRightImageUrl(url: String) {
        dataStore.edit { preferences ->
            preferences[RIGHT_PICTURE_URL] = url
        }
    }
    suspend fun saveHasLoggedIn(hasLoggedIn:Boolean?){
        dataStore.edit { preferences ->
            preferences[HAS_LOGGED_IN] = hasLoggedIn ?:false
        }
    }

    fun hasLoggedIn() = dataStore.data.map { preferences ->
        preferences[HAS_LOGGED_IN]
    }

    suspend fun saveReferenceImageUrl(url: String) {
        dataStore.edit { preferences ->
            preferences[REFERENCE_PICTURE_URL] = url
        }
    }

    // Get Firebase URLs
    fun getFrontImageUrl() = dataStore.data.map { preferences ->
        preferences[FRONT_PICTURE_URL]
    }

    fun getBackImageUrl() = dataStore.data.map { preferences ->
        preferences[BACK_PICTURE_URL]
    }

    fun getLeftImageUrl() = dataStore.data.map { preferences ->
        preferences[LEFT_PICTURE_URL]
    }

    fun getRightImageUrl() = dataStore.data.map { preferences ->
        preferences[RIGHT_PICTURE_URL]
    }

    fun getReferenceImageUrl() = dataStore.data.map { preferences ->
        preferences[REFERENCE_PICTURE_URL]
    }


    suspend fun clearSavedImages() {
        dataStore.edit { preferences ->
            preferences.remove(FRONT_PICTURE)
            preferences.remove(BACK_PICTURE)
            preferences.remove(LEFT_PICTURE)
            preferences.remove(RIGHT_PICTURE)
            preferences.remove(FRONT_PICTURE_URL)
            preferences.remove(BACK_PICTURE_URL)
            preferences.remove(LEFT_PICTURE_URL)
            preferences.remove(RIGHT_PICTURE_URL)
            preferences.remove(REFERENCE_PICTURE_URL)
            preferences.remove(MEASUREMENT_MEN)
            preferences.remove(MEASUREMENT_WOMEN)
            preferences.remove(MEASUREMENT_UNISEX)
            preferences.remove(SIZE_CATEGORY)
        }
    }

    suspend fun clearAllData() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }


}