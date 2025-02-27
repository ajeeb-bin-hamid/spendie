package com.ajeeb.spendie.main.presentation.ui.home

import androidx.navigation.NavType
import com.ajeeb.spendie.common.domain.model.Expense
import com.ajeeb.spendie.common.presentation.utils.navType
import kotlinx.serialization.Serializable
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@Serializable
data class HomeState(
    val expenses: List<Expense> = arrayListOf()
) {
    companion object {
        val typeMap: Map<KType, NavType<out Any>> = mapOf(
            typeOf<List<Expense>>() to navType<List<Expense>>()
        )
    }
}