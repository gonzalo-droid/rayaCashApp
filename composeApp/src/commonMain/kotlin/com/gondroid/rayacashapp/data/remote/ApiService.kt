package com.gondroid.rayacashapp.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ApiService(private val client: HttpClient) {

    suspend fun getCoinData(coin: String) {
        return client.get("api/v3/coins/markets") {
            parameter("vs_currency", "us")
            parameter("ids", coin)
        }.body()
    }

    suspend fun getCoinPrice(coin: String, vsCurrency: String) {
        return client.get("api/v3/simple/price") {
            parameter("ids", coin)
            parameter("vsCurrency", vsCurrency)
        }.body()
    }
}