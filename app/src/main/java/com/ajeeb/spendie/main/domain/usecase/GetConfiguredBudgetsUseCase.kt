package com.ajeeb.spendie.main.domain.usecase

import com.ajeeb.spendie.main.domain.dto.BudgetResult
import com.ajeeb.spendie.main.domain.repository.MainDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

/**
 * Use case for retrieving the configured budgets from the data store.
 *
 * This class is responsible for fetching the individual budget values
 * for different categories (food & beverage, transport, entertainment, bills,
 * and miscellaneous) from the [MainDataStoreRepository] and combining them
 * into a single [BudgetResult] object.
 *
 * @property mainDataStoreRepository The repository responsible for interacting with the data store.
 */
class GetConfiguredBudgetsUseCase @Inject constructor(
    private val mainDataStoreRepository: MainDataStoreRepository
) {
    operator fun invoke(): Flow<BudgetResult> {
        return combine(
            mainDataStoreRepository.foodAndBevBudget,
            mainDataStoreRepository.transportBudget,
            mainDataStoreRepository.entertainmentBudget,
            mainDataStoreRepository.billsBudget,
            mainDataStoreRepository.miscellaneousBudget
        ) { food, transport, entertainment, bills, miscellaneous ->
            BudgetResult(
                foodAndBevBudget = food,
                transportBudget = transport,
                entertainmentBudget = entertainment,
                billsBudget = bills,
                miscellaneousBudget = miscellaneous
            )
        }
    }
}