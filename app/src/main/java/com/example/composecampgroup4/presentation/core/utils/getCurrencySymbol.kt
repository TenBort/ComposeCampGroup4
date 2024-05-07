package com.example.composecampgroup4.presentation.core.utils

import java.util.Currency

fun getCurrencySymbol(numericCode: Int): String? {
    val currency = Currency.getAvailableCurrencies().firstOrNull { it.numericCode == numericCode }
    return currency?.symbol
}