
package com.gondroid.rayacashapp

interface KMMDecimal {
    val value: String
}

expect fun KMMDecimal.plus(other: KMMDecimal): KMMDecimal

expect fun KMMDecimal.toPlainString(): String

expect fun createDecimal(value: String): KMMDecimal

expect fun KMMDecimal.times(other: KMMDecimal): KMMDecimal

expect fun Long.toKMMDecimal(): KMMDecimal