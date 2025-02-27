package com.ajeeb.spendie.main.presentation.ui.expense

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ajeeb.spendie.common.domain.model.Expense
import com.ajeeb.spendie.common.presentation.utils.currentState
import com.ajeeb.spendie.common.presentation.utils.postSideEffect
import com.ajeeb.spendie.common.presentation.utils.reduceState
import com.ajeeb.spendie.main.domain.enums.CategoryType
import com.ajeeb.spendie.main.domain.usecase.DeleteExpenseUseCase
import com.ajeeb.spendie.main.domain.usecase.InsertNewExpenseUseCase
import com.ajeeb.spendie.main.domain.usecase.UpdateExpenseUseCase
import com.tapes.app.common.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val insertNewExpenseUseCase: InsertNewExpenseUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase
) : ViewModel(), ContainerHost<ExpenseState, ExpenseSideEffect> {

    private val initialState = savedStateHandle.toRoute<ExpenseState>(ExpenseState.typeMap)
    override val container = viewModelScope.container<ExpenseState, ExpenseSideEffect>(initialState)


    fun onEvent(event: ExpenseIntent) {
        when (event) {
            is ExpenseIntent.SetAmount -> setAmount(event.amount)
            is ExpenseIntent.SetCategory -> setCategory(event.category)
            is ExpenseIntent.SetDate -> setDate(event.date)
            is ExpenseIntent.SetNotes -> setNotes(event.notes)
            is ExpenseIntent.SaveExpense -> saveExpense()
            is ExpenseIntent.DeleteExpense -> deleteExpense()
            is ExpenseIntent.UpdateExpense -> updateExpense()
        }
    }

    private fun setAmount(amount: String) {
        viewModelScope.launch {
            reduceState { copy(amount = amount, isErrorOnAmount = false) }
        }
    }

    private fun setCategory(category: CategoryType) {
        viewModelScope.launch {
            reduceState { copy(category = category, isErrorOnCategory = false) }
        }
    }

    private fun setDate(date: String) {
        viewModelScope.launch {
            reduceState { copy(date = date, isErrorOnDate = false) }
        }
    }

    private fun setNotes(notes: String) {
        viewModelScope.launch {
            reduceState { copy(notes = notes, isErrorOnNotes = false) }
        }
    }

    private fun saveExpense() {
        viewModelScope.launch {

            val amount = currentState.amount.toDoubleOrNull()
            val category = currentState.category
            val date = currentState.date
            val note = currentState.notes

            if (amount != null && amount != 0.0 && category != null && !date.isNullOrBlank() && !note.isNullOrBlank()) {
                val expense = Expense(
                    expenseId = 0, amount = amount, category = category, date = date, notes = note
                )
                when (insertNewExpenseUseCase(expense)) {
                    is Result.Success -> postSideEffect {
                        ExpenseSideEffect.ExpenseSaved
                    }

                    is Result.Error -> postSideEffect {
                        ExpenseSideEffect.ShowToast("Unable to insert")
                    }
                }

            } else when {
                amount == null || amount == 0.0 -> {
                    reduceState { copy(isErrorOnAmount = true) }
                }

                category == null -> {
                    reduceState { copy(isErrorOnCategory = true) }
                }

                date.isNullOrBlank() -> {
                    reduceState { copy(isErrorOnDate = true) }
                }

                note.isNullOrBlank() -> {
                    reduceState { copy(isErrorOnNotes = true) }
                }
            }
        }
    }

    private fun deleteExpense() {
        viewModelScope.launch {
            val expenseId = currentState.expenseId
            expenseId?.let {
                when (deleteExpenseUseCase(expenseId)) {
                    is Result.Success -> postSideEffect {
                        ExpenseSideEffect.ExpenseDeleted
                    }

                    is Result.Error -> postSideEffect {
                        ExpenseSideEffect.ShowToast("Unable to delete")
                    }
                }
            }
        }
    }

    private fun updateExpense() {
        viewModelScope.launch {

            val expenseId = currentState.expenseId
            val amount = currentState.amount.toDoubleOrNull()
            val category = currentState.category
            val date = currentState.date
            val note = currentState.notes

            if (expenseId != null && amount != null && amount != 0.0 && category != null && !date.isNullOrBlank() && !note.isNullOrBlank()) {
                val expense = Expense(
                    expenseId = expenseId,
                    amount = amount,
                    category = category,
                    date = date,
                    notes = note
                )
                when (updateExpenseUseCase(expense)) {
                    is Result.Success -> postSideEffect {
                        ExpenseSideEffect.ExpenseSaved
                    }

                    is Result.Error -> postSideEffect {
                        ExpenseSideEffect.ShowToast("Unable to update")
                    }
                }

            } else when {
                amount == null || amount == 0.0 -> {
                    reduceState { copy(isErrorOnAmount = true) }
                }

                category == null -> {
                    reduceState { copy(isErrorOnCategory = true) }
                }

                date.isNullOrBlank() -> {
                    reduceState { copy(isErrorOnDate = true) }
                }

                note.isNullOrBlank() -> {
                    reduceState { copy(isErrorOnNotes = true) }
                }
            }
        }
    }
}