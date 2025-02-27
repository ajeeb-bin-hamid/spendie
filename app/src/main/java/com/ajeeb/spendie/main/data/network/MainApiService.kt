package com.ajeeb.spendie.main.data.network

import com.ajeeb.spendie.main.data.model.ForexResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApiService {

    @GET("rates")
    suspend fun getForexRates(@Query("base") baseCurrency: String): Response<ForexResponse>
}