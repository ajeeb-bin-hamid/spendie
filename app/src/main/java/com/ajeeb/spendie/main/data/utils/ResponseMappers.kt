package com.ajeeb.spendie.main.data.utils

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.ajeeb.spendie.common.data.model.ExpenseTableItem
import com.ajeeb.spendie.common.domain.model.Expense
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun mapToExpensesFlow(rawFlow: Flow<List<ExpenseTableItem>>): Flow<SnapshotStateList<Expense>> {
    return rawFlow.map { response ->
        mutableStateListOf<Expense>().apply {
            response.forEach {
                add(
                    Expense(
                        expenseId = it.expenseId,
                        amount = it.amount,
                        category = it.category,
                        date = it.date,
                        notes = it.notes
                    )
                )
            }
        }
    }
}