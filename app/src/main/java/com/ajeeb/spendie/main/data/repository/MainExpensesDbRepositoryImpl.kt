package com.ajeeb.spendie.main.data.repository

import com.ajeeb.spendie.common.data.model.ExpenseTableItem
import com.ajeeb.spendie.common.domain.model.Expense
import com.ajeeb.spendie.common.domain.utils.Issues
import com.ajeeb.spendie.main.data.db.MainExpensesDbDao
import com.ajeeb.spendie.main.data.utils.mapToExpensesFlow
import com.ajeeb.spendie.main.domain.repository.MainExpensesDbRepository
import com.tapes.app.common.domain.utils.Result
import kotlinx.coroutines.flow.Flow

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

    override fun getAllExpenses(): Flow<List<Expense>> {
        val rawFlow = mainExpensesDbDao.getAllExpenses()
        val formattedFlow = mapToExpensesFlow(rawFlow)
        return formattedFlow
    }

    override suspend fun updateExpense(expense: Expense): Result<Unit, Issues.Database> {

        return try {
            val tableItem = ExpenseTableItem(
                expenseId = expense.expenseId,
                amount = expense.amount,
                category = expense.category,
                date = expense.date,
                notes = expense.notes
            )
            mainExpensesDbDao.updateExpense(tableItem)
            return Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(Issues.Database.Unknown)
        }
    }

    override suspend fun deleteExpense(expenseId: Int): Result<Unit, Issues.Database> {
        return try {
            mainExpensesDbDao.deleteExpenseById(expenseId)
            return Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(Issues.Database.Unknown)
        }
    }


}