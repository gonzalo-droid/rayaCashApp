package com.gondroid.rayacashapp.domain.model

import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import rayacashapp.composeapp.generated.resources.Res
import rayacashapp.composeapp.generated.resources.coin_ars
import rayacashapp.composeapp.generated.resources.coin_bitcoin
import rayacashapp.composeapp.generated.resources.coin_ethereum
import rayacashapp.composeapp.generated.resources.coin_usd

@Serializable
data class Coin(
    val id: String, val currency: Currency, // "ARS", "USD", "BTC", "ETH"
    val name: String, val image: String
) {
    companion object {
        fun getCoinImage(currency: Currency): DrawableResource {
            return when (currency) {
                Currency.ARS -> Res.drawable.coin_ars
                Currency.USD -> Res.drawable.coin_usd
                Currency.BTC -> Res.drawable.coin_bitcoin
                Currency.ETH -> Res.drawable.coin_ethereum
                else -> {
                    Res.drawable.coin_usd
                }
            }
        }

        fun getCoin(currency: Currency): Coin {
            return coinsList.find { it.currency == currency }!!
        }
    }
}

enum class Currency {
    ARS, USD, BTC, ETH, UNKNOWN
}

val coinsList = listOf(
    Coin(
        id = "ars", currency = Currency.ARS, name = "Argentine Peso", image = "coin_ars.png"
    ), Coin(
        id = "usd", currency = Currency.USD, name = "US Dollar", image = "coin_usd.png"
    ), Coin(
        id = "bitcoin", currency = Currency.BTC, name = "Bitcoin", image = "coin_bitcoin.png"
    ), Coin(
        id = "ethereum", currency = Currency.ETH, name = "Ethereum", image = "coin_ethereum.png"
    )
)

fun getCurrency(currency: String): Currency {
    return when (currency) {
        Currency.ARS.name -> Currency.ARS
        Currency.USD.name -> Currency.USD
        Currency.BTC.name -> Currency.BTC
        Currency.ETH.name -> Currency.ETH
        else -> {
            Currency.UNKNOWN
        }
    }
}