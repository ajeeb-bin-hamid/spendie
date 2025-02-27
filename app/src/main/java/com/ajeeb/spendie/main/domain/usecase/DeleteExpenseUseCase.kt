package com.ajeeb.spendie.main.domain.usecase

import com.ajeeb.spendie.main.domain.repository.MainExpensesDbRepository
import javax.inject.Inject

class DeleteExpenseUseCase @Inject constructor(
    private val mainExpensesDbRepository: MainExpensesDbRepository
) {
    suspend operator fun invoke(expenseId: Int) = mainExpensesDbRepository.deleteExpense(expenseId)
}