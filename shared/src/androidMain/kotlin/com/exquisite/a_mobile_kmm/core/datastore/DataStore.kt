@file:JvmName("AndroidDataStore")

package com.exquisite.a_mobile_kmm.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.exquisite.a_mobile_kmm.core.database.datastore.createDataStore
import com.exquisite.a_mobile_kmm.core.database.datastore.dataStoreFileName


fun initDataStore(context: Context): DataStore<Preferences> = createDataStore(
    producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
)