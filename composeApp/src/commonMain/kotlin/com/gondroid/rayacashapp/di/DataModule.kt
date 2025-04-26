package com.gondroid.rayacashapp.di

import com.gondroid.rayacashapp.data.RepositoryImpl
import com.gondroid.rayacashapp.data.remote.ApiService
import com.gondroid.rayacashapp.domain.Repository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

const val COIN_GECKO_API_KEY = "CG-YbdkbHQkbaTUrxLufExLC5f3"
const val API_HOST = "api.coingecko.com"
const val API_KEY = "x-cg-demo-api-key"

val dataModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(json = Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = API_HOST
                    parameters.append(API_KEY, COIN_GECKO_API_KEY)
                }
            }
        }
    }

    factoryOf(::ApiService)
    factory<Repository> { RepositoryImpl(get(), get()) }

}