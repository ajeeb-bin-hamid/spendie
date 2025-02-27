package com.ajeeb.spendie.main.presentation.ui.expense

import androidx.navigation.NavType
import com.ajeeb.spendie.main.domain.enums.CategoryType
import kotlinx.serialization.Serializable
import kotlin.reflect.KType

@Serializable
data class ExpenseState(
    val isLoading: Boolean = false,
    val amount: String = "",
    val category: CategoryType? = null,
    val date: String? = null,
    val notes: String? = "",
    val isErrorOnAmount: Boolean = false
) {
    companion object {
        val typeMap: Map<KType, NavType<out Any>> = mapOf()
    }
}