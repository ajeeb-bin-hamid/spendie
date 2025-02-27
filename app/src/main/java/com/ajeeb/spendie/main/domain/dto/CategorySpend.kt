package com.ajeeb.spendie.main.domain.dto

data class CategorySpend(
    val foodAndBev: Double? = null,
    val transport: Double? = null,
    val entertainment: Double? = null,
    val bills: Double? = null,
    val miscellaneous: Double? = null
)