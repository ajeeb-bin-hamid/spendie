package com.ajeeb.spendie.main.presentation.ui.budget

import androidx.navigation.NavType
import com.ajeeb.spendie.main.domain.enums.CategoryType
import kotlinx.serialization.Serializable
import kotlin.reflect.KType

@Serializable
data class BudgetState(
    val foodAndBevBudget: Double? = 120.0,
    val transportBudget: Double? = null,
    val entertainmentBudget: Double? = null,
    val billsBudget: Double? = null,
    val miscellaneousBudget: Double? = null,
    val foodAndBevSpends: Double = 0.0,
    val transportSpends: Double = 0.0,
    val entertainmentSpends: Double = 0.0,
    val billsSpends: Double = 0.0,
    val miscellaneousSpends: Double = 0.0,
    val dialogFieldText: String = "",
    val isErrorOnBudgetAmount: Boolean = false,
    val dialogCategoryType: CategoryType? = null,
) {
    companion object {
        val typeMap: Map<KType, NavType<out Any>> = mapOf()
    }
}