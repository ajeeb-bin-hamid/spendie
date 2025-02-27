package com.ajeeb.spendie.main.presentation.ui.budget

import com.ajeeb.spendie.main.domain.enums.CategoryType

sealed class BudgetIntent {
    data class SetSheetCategoryType(val categoryType: CategoryType?) : BudgetIntent()
    data class SetDialogFieldValue(val value: String) : BudgetIntent()
    data class SaveBudget(val categoryType: CategoryType) : BudgetIntent()
}