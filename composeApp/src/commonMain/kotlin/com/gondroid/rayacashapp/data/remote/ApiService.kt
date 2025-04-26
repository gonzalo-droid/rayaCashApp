package com.gondroid.rayacashapp.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.json.JsonElement

class ApiService(private val client: HttpClient) {

    /**
     * Fetches the price of one or multiple cryptocurrencies in a specified fiat currency.
     *
     * This function makes a GET request to the endpoint `/api/v3/simple/price` using Ktor client.
     *
     * @param coin A comma-separated list of cryptocurrency IDs to fetch the price for (e.g., "bitcoin,ethereum").
     * @param vsCurrency The fiat currency against which the prices are to be compared (e.g., "ars", "usd").
     * @return A [JsonElement] containing the response from the API, typically a JSON object with the requested prices.
     *
     * Example of expected response:
     * ```
     * {
     *     "bitcoin": {
     *         "ars": 110451698
     *     },
     *     "ethereum": {
     *         "ars": 2095119
     *     },
     *     "usd": {
     *         "ars": 1164.87
     *     }
     * }
     * ```
     * @throws ClientRequestException if the request fails or the server returns an error.
     */
    suspend fun getCoinPrice(coin: String, vsCurrency: String): JsonElement {
        return client.get("api/v3/simple/price") {
            parameter("ids", coin)
            parameter("vs_currencies", vsCurrency)
        }.body()
    }
}