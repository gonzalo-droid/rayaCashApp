package com.gondroid.rayacashapp

import java.math.BigDecimal

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

actual fun Long.toKMMDecimal(): KMMDecimal {
    return createDecimal(this.toString())
}