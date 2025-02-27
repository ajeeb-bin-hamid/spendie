package com.ajeeb.spendie.main.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class BudgetResult(
    val foodAndBevBudget: Double? = null,
    val transportBudget: Double? = null,
    val entertainmentBudget: Double? = null,
    val billsBudget: Double? = null,
    val miscellaneousBudget: Double? = null
)
