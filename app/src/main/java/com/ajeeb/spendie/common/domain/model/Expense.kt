package com.ajeeb.spendie.common.domain.model


import com.ajeeb.spendie.main.domain.enums.CategoryType
import kotlinx.serialization.Serializable

@Serializable
data class Expense(
    val expenseId: Int,
    val amount: Double,
    val category: CategoryType,
    val date: String,
    val notes: String
)
