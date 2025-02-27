package com.ajeeb.spendie.main.domain.repository


import com.ajeeb.spendie.common.domain.model.Expense
import com.ajeeb.spendie.common.domain.utils.Issues
import com.ajeeb.spendie.main.domain.dto.CategorySpend
import com.tapes.app.common.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface MainExpensesDbRepository {

    suspend fun insertExpense(expense: Expense): Result<Unit, Issues.Database>

    fun getAllExpenses(): Flow<List<Expense>>

    suspend fun updateExpense(expense: Expense): Result<Unit, Issues.Database>

    suspend fun deleteExpense(expenseId: Int): Result<Unit, Issues.Database>

    fun getTotalSpendsByCategory(): Flow<CategorySpend>

}