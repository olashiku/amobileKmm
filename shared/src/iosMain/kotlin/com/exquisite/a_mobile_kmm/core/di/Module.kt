package com.exquisite.a_mobile_kmm.core.di

import androidx.room.RoomDatabase
import com.exquisite.a_mobile_kmm.core.database.getDatabaseBuilder
import com.exquisite.a_mobile_kmm.core.database.room.AppDatabase
import com.exquisite.a_mobile_kmm.core.datastore.initDataStore
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual var platformModule: Module = module {

    single<HttpClientEngine> { Darwin.create() }
    single { initDataStore() }
    singleOf(::getDatabaseBuilder).bind<RoomDatabase.Builder<AppDatabase>>()
}