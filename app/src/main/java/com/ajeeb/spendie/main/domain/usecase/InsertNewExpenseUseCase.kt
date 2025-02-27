package com.ajeeb.spendie.main.domain.usecase

import com.ajeeb.spendie.common.domain.model.Expense
import com.ajeeb.spendie.common.domain.utils.Issues
import com.ajeeb.spendie.main.domain.repository.MainExpensesDbRepository
import com.tapes.app.common.domain.utils.Result
import javax.inject.Inject

/**
 * Use case responsible for inserting a new expense into the database.
 *
 * This class encapsulates the logic for adding a new [Expense] record to the database.
 * It interacts with the [MainExpensesDbRepository] to perform the database operation.
 *
 * @property mainExpensesDbRepository The repository responsible for interacting with the expense database.
 */
class InsertNewExpenseUseCase @Inject constructor(
    private val mainExpensesDbRepository: MainExpensesDbRepository
) {
    suspend operator fun invoke(expense: Expense): Result<Unit, Issues.Database> {
        return mainExpensesDbRepository.insertExpense(expense)
    }
}