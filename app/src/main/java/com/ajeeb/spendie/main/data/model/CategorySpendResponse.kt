package com.ajeeb.spendie.main.data.model

import com.ajeeb.spendie.main.domain.enums.CategoryType

data class CategorySpendResponse(
    val category: CategoryType,
    val totalAmount: Double
)
