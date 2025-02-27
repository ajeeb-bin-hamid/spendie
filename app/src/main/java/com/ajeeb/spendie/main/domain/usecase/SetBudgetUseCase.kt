package com.ajeeb.spendie.main.domain.usecase

import com.ajeeb.spendie.main.domain.enums.CategoryType
import com.ajeeb.spendie.main.domain.repository.MainDataStoreRepository
import javax.inject.Inject

class SetBudgetUseCase @Inject constructor(
    private val mainDataStoreRepository: MainDataStoreRepository
) {
    suspend operator fun invoke(categoryType: CategoryType, amount: Double) {
        when (categoryType) {
            CategoryType.FOOD_AND_BEVERAGES -> mainDataStoreRepository.setFoodAndBevBudget(amount)
            CategoryType.TRANSPORT -> mainDataStoreRepository.setTransportBudget(amount)
            CategoryType.ENTERTAINMENT -> mainDataStoreRepository.setEntertainmentBudget(amount)
            CategoryType.BILLS -> mainDataStoreRepository.setBillsBudget(amount)
            CategoryType.MISCELLANEOUS -> mainDataStoreRepository.setMiscellaneousBudget(amount)
        }
    }
}