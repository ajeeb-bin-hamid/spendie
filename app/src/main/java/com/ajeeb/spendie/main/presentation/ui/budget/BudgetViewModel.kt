package com.ajeeb.spendie.main.presentation.ui.budget

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ajeeb.spendie.common.presentation.utils.currentState
import com.ajeeb.spendie.common.presentation.utils.reduceState
import com.ajeeb.spendie.main.domain.enums.CategoryType
import com.ajeeb.spendie.main.domain.usecase.GetConfiguredBudgetsUseCase
import com.ajeeb.spendie.main.domain.usecase.GetSpendsByCategoryUseCase
import com.ajeeb.spendie.main.domain.usecase.SetBudgetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getConfiguredBudgetsUseCase: GetConfiguredBudgetsUseCase,
    private val getSpendsByCategoryUseCase: GetSpendsByCategoryUseCase,
    private val setBudgetUseCase: SetBudgetUseCase
) : ViewModel(), ContainerHost<BudgetState, BudgetSideEffect> {

    private val initialState = savedStateHandle.toRoute<BudgetState>(BudgetState.typeMap)
    override val container = viewModelScope.container<BudgetState, BudgetSideEffect>(initialState)


    fun onEvent(event: BudgetIntent) {
        when (event) {
            is BudgetIntent.SetSheetCategoryType -> setSheetCategoryType(event.categoryType)
            is BudgetIntent.SetDialogFieldValue -> setDialogFieldValue(event.value)
            is BudgetIntent.SaveBudget -> saveBudget(event.categoryType)
        }
    }

    init {
        viewModelScope.launch {
            val budgets = getConfiguredBudgetsUseCase()
            budgets.collect {
                reduceState {
                    copy(
                        foodAndBevBudget = it.foodAndBevBudget,
                        transportBudget = it.transportBudget,
                        entertainmentBudget = it.entertainmentBudget,
                        billsBudget = it.billsBudget,
                        miscellaneousBudget = it.miscellaneousBudget
                    )
                }
            }
        }

        viewModelScope.launch {
            val spends = getSpendsByCategoryUseCase()
            spends.collect {
                reduceState {
                    copy(
                        foodAndBevSpends = it.foodAndBev ?: 0.0,
                        transportSpends = it.transport ?: 0.0,
                        entertainmentSpends = it.entertainment ?: 0.0,
                        billsSpends = it.bills ?: 0.0,
                        miscellaneousSpends = it.miscellaneous ?: 0.0
                    )
                }
            }
        }
    }

    private fun setSheetCategoryType(categoryType: CategoryType?) {
        viewModelScope.launch {
            reduceState { copy(dialogCategoryType = categoryType) }
        }
    }

    private fun setDialogFieldValue(text: String) {
        viewModelScope.launch {
            reduceState { copy(dialogFieldText = text) }
        }
    }

    private fun saveBudget(categoryType: CategoryType) {
        viewModelScope.launch {
            val amountInDouble = currentState.dialogFieldText.toDoubleOrNull()

            if (amountInDouble == null) {
                reduceState { copy(isErrorOnBudgetAmount = true) }
            } else {
                setBudgetUseCase(categoryType, amountInDouble)
                reduceState {
                    copy(
                        dialogCategoryType = null,
                        isErrorOnBudgetAmount = false,
                        dialogFieldText = ""
                    )
                }
            }
        }
    }
}