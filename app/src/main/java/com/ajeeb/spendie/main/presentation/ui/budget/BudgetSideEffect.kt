package com.ajeeb.spendie.main.presentation.ui.budget

sealed class BudgetSideEffect {
    // Actions that can be performed on the UI 
    data class ShowToast(val message: String) : BudgetSideEffect()
}