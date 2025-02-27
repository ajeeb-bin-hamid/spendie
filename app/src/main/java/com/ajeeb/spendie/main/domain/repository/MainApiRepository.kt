package com.ajeeb.spendie.main.domain.repository

import com.ajeeb.spendie.common.domain.utils.Issues
import com.tapes.app.common.domain.utils.Result

interface MainApiRepository {

    suspend fun getForexRate(): Result<Double, Issues.Network>
}