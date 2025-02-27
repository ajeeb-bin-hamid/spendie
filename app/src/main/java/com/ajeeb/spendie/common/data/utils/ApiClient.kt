package com.ajeeb.spendie.common.data.utils

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ApiClient @Inject constructor() {

    val instance: OkHttpClient by lazy {
        OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES).build()
    }

}