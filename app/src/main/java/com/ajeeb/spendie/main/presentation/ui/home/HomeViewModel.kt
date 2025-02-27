package com.ajeeb.spendie.main.presentation.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ajeeb.spendie.common.presentation.utils.reduceState
import com.ajeeb.spendie.main.domain.usecase.GetAllExpensesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, private val getAllExpensesUseCase: GetAllExpensesUseCase
) : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {

    private val initialState = savedStateHandle.toRoute<HomeState>(HomeState.typeMap)
    override val container = viewModelScope.container<HomeState, HomeSideEffect>(initialState)


    fun onEvent(event: HomeIntent) {
        //
    }

    init {
        viewModelScope.launch {
            val expenses = getAllExpensesUseCase()
            expenses.collect {
                reduceState { copy(expenses = it) }
            }
        }
    }
}