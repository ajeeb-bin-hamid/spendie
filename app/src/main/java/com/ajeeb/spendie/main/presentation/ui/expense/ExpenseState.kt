package com.ajeeb.spendie.main.presentation.ui.expense

import androidx.navigation.NavType
import com.ajeeb.spendie.common.domain.model.Expense
import com.ajeeb.spendie.common.presentation.utils.SpendieNavType
import kotlinx.serialization.Serializable
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@Serializable
data class ExpenseState(
    val isLoading: Boolean = false, val expense: Expense? = null
) {
    companion object {
        val typeMap: Map<KType, NavType<out Any>> = mapOf(
            typeOf<Expense>() to SpendieNavType(Expense.serializer()),
            typeOf<Expense?>() to SpendieNavType(Expense.serializer()),
        )
    }
}