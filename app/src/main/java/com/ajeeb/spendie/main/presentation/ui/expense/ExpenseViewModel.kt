package com.ajeeb.spendie.main.presentation.ui.expense

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import javax.inject.Inject

class ExpenseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel(), ContainerHost<ExpenseState, ExpenseSideEffect> {

    private val initialState = savedStateHandle.toRoute<ExpenseState>(ExpenseState.typeMap)
    override val container = viewModelScope.container<ExpenseState, ExpenseSideEffect>(initialState)


    fun onEvent(event: ExpenseIntent) {
        //
    }
}