package com.ajeeb.spendie.main.presentation.ui.insights

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ajeeb.spendie.common.presentation.utils.currentState
import com.ajeeb.spendie.common.presentation.utils.postSideEffect
import com.ajeeb.spendie.common.presentation.utils.reduceState
import com.ajeeb.spendie.main.domain.usecase.GetForexRateUseCase
import com.ajeeb.spendie.main.domain.usecase.GetSpendsByCategoryUseCase
import com.ajeeb.spendie.main.presentation.enums.CurrencyType
import com.tapes.app.common.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import javax.inject.Inject

@HiltViewModel
class InsightsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSpendsByCategoryUseCase: GetSpendsByCategoryUseCase,
    private val getForexRateUseCase: GetForexRateUseCase
) : ViewModel(), ContainerHost<InsightsState, InsightsSideEffect> {

    private val initialState = savedStateHandle.toRoute<InsightsState>(InsightsState.typeMap)
    override val container =
        viewModelScope.container<InsightsState, InsightsSideEffect>(initialState)


    fun onEvent(event: InsightsIntent) {
        when (event) {
            is InsightsIntent.ToggleCurrency -> toggleCurrency()
        }
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

        viewModelScope.launch {
            when (val forexCall = getForexRateUseCase()) {
                is Result.Success -> {
                    reduceState { copy(usdValue = forexCall.data) }
                }

                is Result.Error -> postSideEffect {
                    InsightsSideEffect.ShowToast("Unable to fetch forex rate")
                }
            }
        }
    }

    private fun toggleCurrency() {
        viewModelScope.launch {
            val usdValue = currentState.usdValue
            if (usdValue != null) {
                when (currentState.currencyType) {
                    CurrencyType.INR -> reduceState { copy(currencyType = CurrencyType.USD) }
                    CurrencyType.USD -> reduceState { copy(currencyType = CurrencyType.INR) }
                }
            } else postSideEffect {
                InsightsSideEffect.ShowToast("Wait till forex rate is fetched")
            }
        }
    }
}