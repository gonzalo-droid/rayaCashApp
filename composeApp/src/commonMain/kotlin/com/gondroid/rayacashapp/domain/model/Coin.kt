package com.gondroid.rayacashapp.domain.model

import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import rayacashapp.composeapp.generated.resources.Res
import rayacashapp.composeapp.generated.resources.coin_ars
import rayacashapp.composeapp.generated.resources.coin_bitcoin
import rayacashapp.composeapp.generated.resources.coin_ethereum
import rayacashapp.composeapp.generated.resources.coin_usd

@Serializable
data class Coin(
    val id: String,
    val currency: CurrencyType,
    val name: String,
) {
    companion object {
        fun getCoinImage(currency: CurrencyType): DrawableResource {
            return when (currency) {
                CurrencyType.ARS -> Res.drawable.coin_ars
                CurrencyType.USD -> Res.drawable.coin_usd
                CurrencyType.BTC -> Res.drawable.coin_bitcoin
                CurrencyType.ETH -> Res.drawable.coin_ethereum
            }
        }

        fun getCoin(currency: CurrencyType): Coin {
            return coinsList.find { it.currency == currency }!!
        }
    }
}


val coinsList = listOf(
    Coin(
        id = "ars", currency = CurrencyType.ARS, name = "Argentine Peso"
    ), Coin(
        id = "usd", currency = CurrencyType.USD, name = "US Dollar"
    ), Coin(
        id = "bitcoin", currency = CurrencyType.BTC, name = "Bitcoin"
    ), Coin(
        id = "ethereum", currency = CurrencyType.ETH, name = "Ethereum"
    )
)

fun getCurrency(currency: String): CurrencyType {
    return when (currency) {
        CurrencyType.ARS.name -> CurrencyType.ARS
        CurrencyType.USD.name -> CurrencyType.USD
        CurrencyType.BTC.name -> CurrencyType.BTC
        CurrencyType.ETH.name -> CurrencyType.ETH
        else -> CurrencyType.ARS
    }
}

