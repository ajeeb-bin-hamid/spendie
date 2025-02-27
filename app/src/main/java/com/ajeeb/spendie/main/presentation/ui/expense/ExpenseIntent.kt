package com.ajeeb.spendie.main.presentation.ui.expense

import com.ajeeb.spendie.main.domain.enums.CategoryType

sealed class ExpenseIntent {
    data class SetAmount(val amount: String) : ExpenseIntent()
    data class SetCategory(val category: CategoryType) : ExpenseIntent()
    data class SetDate(val date: String) : ExpenseIntent()
    data class SetNotes(val notes: String) : ExpenseIntent()
    data object SaveExpense : ExpenseIntent()
    data object UpdateExpense : ExpenseIntent()
    data object DeleteExpense : ExpenseIntent()
}