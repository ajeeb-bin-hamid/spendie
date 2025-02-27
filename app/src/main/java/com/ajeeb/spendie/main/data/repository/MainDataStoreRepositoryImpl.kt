package com.ajeeb.spendie.main.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.ajeeb.spendie.main.data.utils.BILLS_BUDGET
import com.ajeeb.spendie.main.data.utils.ENTERTAINMENT_BUDGET
import com.ajeeb.spendie.main.data.utils.FOOD_AND_BEV_BUDGET
import com.ajeeb.spendie.main.data.utils.MISCELLANEOUS_BUDGET
import com.ajeeb.spendie.main.data.utils.TRANSPORT_BUDGET
import com.ajeeb.spendie.main.domain.repository.MainDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MainDataStoreRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : MainDataStoreRepository {

    override val foodAndBevBudget: Flow<Double?>
        get() = dataStore.data.map { it[FOOD_AND_BEV_BUDGET] }

    override suspend fun setFoodAndBevBudget(amount: Double) {
        dataStore.edit {
            it[FOOD_AND_BEV_BUDGET] = amount
        }
    }

    override val transportBudget: Flow<Double?>
        get() = dataStore.data.map { it[TRANSPORT_BUDGET] }

    override suspend fun setTransportBudget(amount: Double) {
        dataStore.edit {
            it[TRANSPORT_BUDGET] = amount
        }
    }

    override val entertainmentBudget: Flow<Double?>
        get() = dataStore.data.map { it[ENTERTAINMENT_BUDGET] }

    override suspend fun setEntertainmentBudget(amount: Double) {
        dataStore.edit {
            it[ENTERTAINMENT_BUDGET] = amount
        }
    }

    override val billsBudget: Flow<Double?>
        get() = dataStore.data.map { it[BILLS_BUDGET] }

    override suspend fun setBillsBudget(amount: Double) {
        dataStore.edit {
            it[BILLS_BUDGET] = amount
        }
    }

    override val miscellaneousBudget: Flow<Double?>
        get() = dataStore.data.map { it[MISCELLANEOUS_BUDGET] }

    override suspend fun setMiscellaneousBudget(amount: Double) {
        dataStore.edit {
            it[MISCELLANEOUS_BUDGET] = amount
        }
    }

}