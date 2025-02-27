package com.ajeeb.spendie.main.domain.repository

import kotlinx.coroutines.flow.Flow

interface MainDataStoreRepository {

    val foodAndBevBudget: Flow<Double?>
    suspend fun setFoodAndBevBudget(amount: Double)

    val transportBudget: Flow<Double?>
    suspend fun setTransportBudget(amount: Double)

    val entertainmentBudget: Flow<Double?>
    suspend fun setEntertainmentBudget(amount: Double)

    val billsBudget: Flow<Double?>
    suspend fun setBillsBudget(amount: Double)

    val miscellaneousBudget: Flow<Double?>
    suspend fun setMiscellaneousBudget(amount: Double)

}