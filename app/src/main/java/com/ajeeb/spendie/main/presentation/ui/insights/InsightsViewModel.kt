package com.ajeeb.spendie.main.presentation.ui.insights

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ajeeb.spendie.common.presentation.utils.reduceState
import com.ajeeb.spendie.main.domain.usecase.GetSpendsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import javax.inject.Inject

@HiltViewModel
class InsightsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSpendsByCategoryUseCase: GetSpendsByCategoryUseCase
) : ViewModel(), ContainerHost<InsightsState, InsightsSideEffect> {

    private val initialState = savedStateHandle.toRoute<InsightsState>(InsightsState.typeMap)
    override val container =
        viewModelScope.container<InsightsState, InsightsSideEffect>(initialState)


    fun onEvent(event: InsightsIntent) {
        //
    }

    init {
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
}