package com.example.evalp3.data

import com.example.evalp3.data.local.AppDatabase
import com.example.evalp3.data.local.LocationDao
import com.example.evalp3.data.local.buildAppDatabase
import com.example.evalp3.data.remote.CharacterApi
import com.example.evalp3.data.remote.LocationApi
import com.example.evalp3.data.remote.createHttpClient
import com.example.evalp3.data.repositories.LocationRepositoryImpl
import com.example.evalp3.domain.location.LocationRepository
import io.ktor.client.HttpClient
import org.koin.dsl.module

val remoteModule = module {
    single<HttpClient> { createHttpClient() }
    single { LocationApi(get()) }
    single { CharacterApi(get()) }
}

val databaseModule = module {
    single<AppDatabase> { buildAppDatabase(get()) }
    single<LocationDao> { get<AppDatabase>().locationDao() }
}

val repositoriesModule = module {
    single<LocationRepository> { LocationRepositoryImpl(get(), get(), get(), get()) }
}
