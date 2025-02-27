package com.ajeeb.spendie.main.data.repository

import com.ajeeb.spendie.common.data.utils.handledApiCall
import com.ajeeb.spendie.common.domain.utils.Issues
import com.ajeeb.spendie.main.data.network.MainApiService
import com.ajeeb.spendie.main.data.utils.INR
import com.ajeeb.spendie.main.domain.repository.MainApiRepository
import com.tapes.app.common.domain.utils.Result

class MainApiRepositoryImpl(
    private val mainApiService: MainApiService
) : MainApiRepository {

    override suspend fun getForexRate(): Result<Double, Issues.Network> {

        val apiCall = handledApiCall {
            mainApiService.getForexRates(INR)
        }

        return when (apiCall) {
            is Result.Success -> {
                val usdRate = apiCall.data.rates?.usd
                println("mylog, ${usdRate}")
                println("mylog, ${apiCall.data}")
                if (usdRate != null) {
                    Result.Success(usdRate)
                } else {
                    Result.Error(Issues.Network.MappingFailed)
                }
            }

            is Result.Error -> Result.Error(apiCall.error)
        }
    }
}