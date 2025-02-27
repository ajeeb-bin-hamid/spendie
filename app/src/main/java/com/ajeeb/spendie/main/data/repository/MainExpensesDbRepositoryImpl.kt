package com.ajeeb.spendie.main.data.repository

import com.ajeeb.spendie.common.data.model.ExpenseTableItem
import com.ajeeb.spendie.common.domain.model.Expense
import com.ajeeb.spendie.common.domain.utils.Issues
import com.ajeeb.spendie.main.data.db.MainExpensesDbDao
import com.ajeeb.spendie.main.domain.repository.MainExpensesDbRepository
import com.tapes.app.common.domain.utils.Result

class MainExpensesDbRepositoryImpl(
    private val mainExpensesDbDao: MainExpensesDbDao
) : MainExpensesDbRepository {

    override suspend fun insertExpense(expense: Expense): Result<Unit, Issues.Database> {
        val expenseTableItem = ExpenseTableItem(
            amount = expense.amount,
            category = expense.category,
            date = expense.date,
            notes = expense.notes
        )

        return try {
            mainExpensesDbDao.insertExpense(expenseTableItem)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(Issues.Database.Unknown)
        }
    }
}