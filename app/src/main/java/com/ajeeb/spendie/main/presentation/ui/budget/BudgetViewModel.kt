package com.ajeeb.spendie.main.presentation.ui.budget

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel(), ContainerHost<BudgetState, BudgetSideEffect> {

    private val initialState = savedStateHandle.toRoute<BudgetState>()
    override val container = viewModelScope.container<BudgetState, BudgetSideEffect>(initialState)


    fun onEvent(event: BudgetIntent) {
        //
    }
}