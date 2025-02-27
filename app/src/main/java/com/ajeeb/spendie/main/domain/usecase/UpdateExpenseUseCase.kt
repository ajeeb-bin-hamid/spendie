package com.ajeeb.spendie.main.domain.usecase

import com.ajeeb.spendie.common.domain.model.Expense
import com.ajeeb.spendie.main.domain.repository.MainExpensesDbRepository
import javax.inject.Inject

class UpdateExpenseUseCase @Inject constructor(
    private val mainExpensesDbRepository: MainExpensesDbRepository
) {
    suspend operator fun invoke(expense: Expense) = mainExpensesDbRepository.updateExpense(expense)
}