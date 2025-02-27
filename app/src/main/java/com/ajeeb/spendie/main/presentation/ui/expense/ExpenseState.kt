package com.ajeeb.spendie.main.presentation.ui.expense

import androidx.navigation.NavType
import com.ajeeb.spendie.main.domain.enums.CategoryType
import kotlinx.serialization.Serializable
import kotlin.reflect.KType

@Serializable
data class ExpenseState(
    val amount: String = "",
    val category: CategoryType? = null,
    val date: String? = null,
    val notes: String? = "",
    val isErrorOnAmount: Boolean = false,
    val isErrorOnCategory: Boolean = false,
    val isErrorOnDate: Boolean = false,
    val isErrorOnNotes: Boolean = false,
    val isEditMode: Boolean = false,
    val expenseId: Int? = null
) {
    companion object {
        val typeMap: Map<KType, NavType<out Any>> = mapOf()
    }
}