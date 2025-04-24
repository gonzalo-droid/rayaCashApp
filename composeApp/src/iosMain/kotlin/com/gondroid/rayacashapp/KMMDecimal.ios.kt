package com.gondroid.rayacashapp

import platform.Foundation.NSDecimalNumber

class IOSDecimal(override val value: String) : KMMDecimal {
    val decimal = NSDecimalNumber(value)
}

actual fun KMMDecimal.plus(other: KMMDecimal): KMMDecimal {
    val thisDecimal = (this as IOSDecimal).decimal
    val otherDecimal = (other as IOSDecimal).decimal
    return IOSDecimal(thisDecimal.decimalNumberByAdding(otherDecimal).stringValue)
}

actual fun KMMDecimal.toPlainString(): String {
    return (this as IOSDecimal).decimal.stringValue
}

actual fun createDecimal(value: String): KMMDecimal = IOSDecimal(value)

actual fun KMMDecimal.times(other: KMMDecimal): KMMDecimal {
    val thisDecimal = (this as IOSDecimal).decimal
    val otherDecimal = (other as IOSDecimal).decimal
    return IOSDecimal(thisDecimal.decimalNumberByMultiplyingBy(otherDecimal).stringValue)
}

actual fun Long.toKMMDecimal(): KMMDecimal {
    return createDecimal(this.toString())
}