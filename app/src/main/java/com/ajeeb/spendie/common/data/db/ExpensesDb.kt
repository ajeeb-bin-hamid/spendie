package com.ajeeb.spendie.common.data.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.ajeeb.spendie.common.data.model.ExpenseTableItem
import com.ajeeb.spendie.main.data.db.MainExpensesDbDao

@Database(entities = [ExpenseTableItem::class], version = 1)
abstract class ExpensesDb : RoomDatabase() {
    abstract val mainExpensesDbDao: MainExpensesDbDao
}