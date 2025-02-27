package com.ajeeb.spendie.main.domain.repository


import com.ajeeb.spendie.common.domain.model.Expense
import com.ajeeb.spendie.common.domain.utils.Issues
import com.tapes.app.common.domain.utils.Result

interface MainExpensesDbRepository {

    suspend fun insertExpense(expense: Expense): Result<Unit, Issues.Database>

}