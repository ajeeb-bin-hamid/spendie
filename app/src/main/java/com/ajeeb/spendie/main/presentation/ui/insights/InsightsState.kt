package com.ajeeb.spendie.main.presentation.ui.insights

import androidx.navigation.NavType
import com.ajeeb.spendie.main.presentation.enums.CurrencyType
import kotlinx.serialization.Serializable
import kotlin.reflect.KType

@Serializable
data class InsightsState(
    val foodAndBevSpends: Double = 0.0,
    val transportSpends: Double = 0.0,
    val entertainmentSpends: Double = 0.0,
    val billsSpends: Double = 0.0,
    val miscellaneousSpends: Double = 0.0,
    val usdValue: Double? = null,
    val currencyType: CurrencyType = CurrencyType.INR
) {
    companion object {
        val typeMap: Map<KType, NavType<out Any>> = mapOf()
    }
}