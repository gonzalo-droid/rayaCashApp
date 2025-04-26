package com.gondroid.rayacashapp

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class AndroidDecimal(override val value: String) : KMMDecimal {
    val decimal: BigDecimal = BigDecimal(value)
}

actual fun KMMDecimal.plus(other: KMMDecimal): KMMDecimal {
    val thisDecimal = (this as AndroidDecimal).decimal
    val otherDecimal = (other as AndroidDecimal).decimal
    return AndroidDecimal(thisDecimal.add(otherDecimal).toPlainString())
}

actual fun KMMDecimal.toPlainString(): String {
    return (this as AndroidDecimal).decimal.toPlainString()
}

actual fun createDecimal(value: String): KMMDecimal = AndroidDecimal(value)

actual fun KMMDecimal.times(other: KMMDecimal): KMMDecimal {
    val thisDecimal = (this as AndroidDecimal).decimal
    val otherDecimal = (other as AndroidDecimal).decimal
    return AndroidDecimal(thisDecimal.multiply(otherDecimal).toPlainString())
}

actual fun KMMDecimal.roundToDecimal(decimal: Int): KMMDecimal {
    val rounded = (this as AndroidDecimal)
        .decimal.setScale(decimal, RoundingMode.HALF_UP)
    return AndroidDecimal(rounded.toPlainString())
}

actual fun KMMDecimal.div(other: KMMDecimal): KMMDecimal {
    val thisDecimal = (this as AndroidDecimal).decimal
    val otherDecimal = (other as AndroidDecimal).decimal
    val result = thisDecimal.divide(otherDecimal, 10, RoundingMode.HALF_UP) // escala de 10 decimales
    return AndroidDecimal(result.setScale(10, RoundingMode.HALF_UP).toPlainString())
}
