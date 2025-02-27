package com.ajeeb.spendie.common.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ajeeb.spendie.common.data.EXPENSE_TABLE
import com.ajeeb.spendie.main.domain.enums.CategoryType
import kotlinx.serialization.Serializable

@Entity(tableName = EXPENSE_TABLE)
@Serializable
data class ExpenseTableItem(
    @PrimaryKey(autoGenerate = true) val expenseId: Int = 0,
    val amount: Double,
    val category: CategoryType,
    val date: String,
    val notes: String?
)