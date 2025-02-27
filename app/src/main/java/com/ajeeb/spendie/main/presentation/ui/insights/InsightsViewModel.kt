package com.ajeeb.spendie.main.presentation.ui.insights

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import javax.inject.Inject

@HiltViewModel
class InsightsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel(), ContainerHost<InsightsState, InsightsSideEffect> {

    private val initialState = savedStateHandle.toRoute<InsightsState>()
    override val container =
        viewModelScope.container<InsightsState, InsightsSideEffect>(initialState)


    fun onEvent(event: InsightsIntent) {
        //
    }
}