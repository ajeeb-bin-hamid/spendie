package com.ajeeb.spendie.main.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ajeeb.spendie.common.data.model.ExpenseTableItem

@Dao
interface MainExpensesDbDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExpense(expense: ExpenseTableItem)
}