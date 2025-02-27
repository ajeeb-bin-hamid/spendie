package com.ajeeb.spendie.main.data.model

import com.google.gson.annotations.SerializedName

data class ForexResponse(
    @SerializedName("date") val date: String? = null,
    @SerializedName("base") val base: String? = null,
    @SerializedName("rates") val rates: Rates? = null,
) {
    data class Rates(
        @SerializedName("USD") val usd: Double? = null,
    )
}
