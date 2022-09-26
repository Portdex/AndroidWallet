package com.application.portdex.core.utils

import kotlin.random.Random

object RandomUtil {

    const val ALPHABETIC_UPPERCASE_SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    const val ALPHABETIC_LOWERCASE_SYMBOLS = "abcdefghijklmnopqrstuvwxyz"
    const val NUMERIC_SYMBOLS = "0123456789"
    const val SPECIAL_SYMBOLS = "$&@?<>~!%#"
    const val ALPHANUMERIC_AND_SPECIAL_SYMBOLS = ALPHABETIC_UPPERCASE_SYMBOLS +
            ALPHABETIC_LOWERCASE_SYMBOLS +
            NUMERIC_SYMBOLS +
            SPECIAL_SYMBOLS

    fun generateKey(length: Int): String {
        val chars: MutableList<Char> = ArrayList(length)
        var hasUpper = false
        var hasLower = false
        var hasNumber = false
        var hasSpecial = false
        for (i in 0 until length) {
            // Make sure we have at least one upper, lower, number and special character.
            // Then, fill randomly from set containing all characters.
            if (!hasUpper) {
                chars.add(
                    ALPHABETIC_UPPERCASE_SYMBOLS[Random.nextInt(
                        0,
                        ALPHABETIC_UPPERCASE_SYMBOLS.length
                    )]
                )
                hasUpper = true
            } else if (!hasLower) {
                chars.add(
                    ALPHABETIC_LOWERCASE_SYMBOLS[Random.nextInt(
                        0,
                        ALPHABETIC_LOWERCASE_SYMBOLS.length
                    )]
                )
                hasLower = true
            } else if (!hasNumber) {
                chars.add(NUMERIC_SYMBOLS[Random.nextInt(0, NUMERIC_SYMBOLS.length)])
                hasNumber = true
            } else if (!hasSpecial) {
                chars.add(SPECIAL_SYMBOLS[Random.nextInt(0, SPECIAL_SYMBOLS.length)])
                hasSpecial = true
            } else {
                chars.add(
                    ALPHANUMERIC_AND_SPECIAL_SYMBOLS[Random.nextInt(
                        0,
                        ALPHANUMERIC_AND_SPECIAL_SYMBOLS.length
                    )]
                )
            }
        }
        // Shuffle characters to mix up the first 4 characters
        chars.shuffle()
        return chars.joinToString(separator = "")
    }
}