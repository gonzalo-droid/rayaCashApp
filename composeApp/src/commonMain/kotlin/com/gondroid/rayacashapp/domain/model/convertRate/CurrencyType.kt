package com.gondroid.rayacashapp.domain.model.convertRate

import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType.ARS
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType.BTC
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType.ETH
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType.USD
import com.gondroid.rayacashapp.shared.KMMDecimal
import com.gondroid.rayacashapp.shared.div
import com.gondroid.rayacashapp.shared.roundToDecimal
import com.gondroid.rayacashapp.shared.times
import org.jetbrains.compose.resources.DrawableResource
import rayacashapp.composeapp.generated.resources.Res
import rayacashapp.composeapp.generated.resources.coin_ars
import rayacashapp.composeapp.generated.resources.coin_bitcoin
import rayacashapp.composeapp.generated.resources.coin_ethereum
import rayacashapp.composeapp.generated.resources.coin_usd

enum class CurrencyType(
    val id: String,
    val label: String,
    val icon: DrawableResource,
    val decimal: Int = 2
) {
    ARS(id = "ars", label = "Argentine Peso", icon = Res.drawable.coin_ars, decimal = 2),
    USD(id = "usd", label = "US Dollar", icon = Res.drawable.coin_usd, decimal = 2),
    BTC(id = "bitcoin", label = "Bitcoin", icon = Res.drawable.coin_bitcoin, decimal = 10),
    ETH(id = "ethereum", label = "Ethereum", icon = Res.drawable.coin_ethereum, decimal = 10),
}

val currencyList: List<CurrencyType> = CurrencyType.entries


fun getCurrencyTypeFromString(coin: String): CurrencyType? {
    return when (coin.lowercase()) {
        "bitcoin" -> BTC
        "ethereum" -> ETH
        "usd" -> USD
        else -> null
    }
}

fun getCurrency(currency: String): CurrencyType {
    return when (currency.lowercase()) {
        ARS.name.lowercase() -> ARS
        USD.name.lowercase() -> USD
        BTC.name.lowercase() -> BTC
        else -> ETH
    }
}

data class Currency(
    val type: CurrencyType,
    override val value: KMMDecimal
) : Convertible {

    override suspend fun convertTo(target: Convertible, rates: ConversionRateProvider): KMMDecimal {
        require(target is Currency)

        val fromRate = rates.getRate(type)
        val toRate = rates.getRate(target.type)

        val amount = when {
            type == ARS && target.type != ARS -> value.div(toRate)
            type != ARS && target.type == ARS -> value.times(fromRate)
            type == ARS && target.type == ARS -> value
            else -> value.times(fromRate).div(toRate)
        }
        return when {
            target.type == ARS || target.type == USD -> amount.roundToDecimal(2)
            else -> amount.roundToDecimal(10)
        }
    }
}