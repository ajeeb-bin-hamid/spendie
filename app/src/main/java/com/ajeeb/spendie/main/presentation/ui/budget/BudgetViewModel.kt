package com.ajeeb.spendie.main.presentation.ui.budget

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ajeeb.spendie.common.presentation.utils.reduceState
import com.ajeeb.spendie.main.domain.usecase.GetConfiguredBudgetsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getConfiguredBudgetsUseCase: GetConfiguredBudgetsUseCase
) : ViewModel(), ContainerHost<BudgetState, BudgetSideEffect> {

    private val initialState = savedStateHandle.toRoute<BudgetState>(BudgetState.typeMap)
    override val container = viewModelScope.container<BudgetState, BudgetSideEffect>(initialState)


    fun onEvent(event: BudgetIntent) {
        //
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
    }
}