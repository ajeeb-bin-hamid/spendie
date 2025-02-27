package com.ajeeb.spendie.main.data.utils

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.ajeeb.spendie.common.data.model.ExpenseTableItem
import com.ajeeb.spendie.common.domain.model.Expense
import com.ajeeb.spendie.main.data.model.CategorySpendResponse
import com.ajeeb.spendie.main.domain.dto.CategorySpend
import com.ajeeb.spendie.main.domain.enums.CategoryType
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

fun mapToCategorySpendsFlow(rawFlow: Flow<List<CategorySpendResponse>>): Flow<CategorySpend> {
    return rawFlow.map { response ->

        var foodAndBev: Double? = null
        var transport: Double? = null
        var entertainment: Double? = null
        var bills: Double? = null
        var miscellaneous: Double? = null

        response.forEach {
            when (it.category) {
                CategoryType.FOOD_AND_BEVERAGES -> foodAndBev = it.totalAmount
                CategoryType.TRANSPORT -> transport = it.totalAmount
                CategoryType.ENTERTAINMENT -> entertainment = it.totalAmount
                CategoryType.BILLS -> bills = it.totalAmount
                CategoryType.MISCELLANEOUS -> miscellaneous = it.totalAmount
            }
        }

        CategorySpend(
            foodAndBev = foodAndBev,
            transport = transport,
            entertainment = entertainment,
            bills = bills,
            miscellaneous = miscellaneous
        )
    }
}