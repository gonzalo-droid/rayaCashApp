package com.gondroid.rayacashapp

import platform.Foundation.NSDecimalNumber
import platform.Foundation.NSDecimalNumberHandler
import platform.Foundation.NSRoundingMode

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

actual fun KMMDecimal.roundToDecimal(decimal: Int): KMMDecimal {
    val decimalNumber = (this as IOSDecimal).decimal
    val handler = NSDecimalNumberHandler(
        roundingMode = NSRoundingMode.NSRoundPlain,
        scale = decimal.toShort(),
        raiseOnExactness = false,
        raiseOnOverflow = false,
        raiseOnUnderflow = false,
        raiseOnDivideByZero = false
    )

    val rounded = decimalNumber.decimalNumberByRoundingAccordingToBehavior(handler)
    return IOSDecimal(rounded.stringValue)
}

actual fun KMMDecimal.div(other: KMMDecimal): KMMDecimal {
    val thisDecimal = (this as IOSDecimal).decimal
    val otherDecimal = (other as IOSDecimal).decimal

    val behavior = NSDecimalNumberHandler.decimalNumberHandlerWithRoundingMode(
        roundingMode = NSRoundingMode.NSRoundPlain,
        scale = 10,
        raiseOnExactness = false,
        raiseOnOverflow = false,
        raiseOnUnderflow = false,
        raiseOnDivideByZero = false
    )

    val result = thisDecimal.decimalNumberByDividingBy(otherDecimal, withBehavior = behavior)
    return IOSDecimal(result.stringValue)
}
