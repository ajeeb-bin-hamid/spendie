package com.ajeeb.spendie.main.domain.usecase

import com.ajeeb.spendie.common.domain.utils.Issues
import com.ajeeb.spendie.main.domain.repository.MainApiRepository
import com.tapes.app.common.domain.utils.Result
import javax.inject.Inject

class GetForexRateUseCase @Inject constructor(
    private val mainApiRepository: MainApiRepository
) {
    suspend operator fun invoke(): Result<Double, Issues.Network> {
        return mainApiRepository.getForexRate()
    }
}