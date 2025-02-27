package com.ajeeb.spendie.main.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ajeeb.spendie.common.data.EXPENSE_TABLE
import com.ajeeb.spendie.common.data.model.ExpenseTableItem
import com.ajeeb.spendie.main.data.model.CategorySpendResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface MainExpensesDbDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExpense(expense: ExpenseTableItem)

    @Query("SELECT * FROM $EXPENSE_TABLE ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<ExpenseTableItem>>

    @Update
    suspend fun updateExpense(expense: ExpenseTableItem)

    @Query("DELETE FROM $EXPENSE_TABLE WHERE expenseId = :id")
    suspend fun deleteExpenseById(id: Int)

    @Query("SELECT category, SUM(amount) as totalAmount FROM $EXPENSE_TABLE GROUP BY category")
    fun getTotalSpendsByCategory(): Flow<List<CategorySpendResponse>>

}