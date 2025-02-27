package com.ajeeb.spendie.main.domain.usecase

import com.ajeeb.spendie.main.domain.repository.MainExpensesDbRepository
import javax.inject.Inject

class GetSpendsByCategoryUseCase @Inject constructor(
    private val mainExpensesDbRepository: MainExpensesDbRepository
) {
    operator fun invoke() = mainExpensesDbRepository.getTotalSpendsByCategory()
}