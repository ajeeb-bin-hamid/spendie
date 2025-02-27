package com.ajeeb.spendie.main.presentation.ui.expense

sealed class ExpenseSideEffect {
    // Actions that can be performed on the UI 
    data class ShowToast(val message: String) : ExpenseSideEffect()
    data object ExpenseSaved : ExpenseSideEffect()
    data object ExpenseDeleted : ExpenseSideEffect()
}