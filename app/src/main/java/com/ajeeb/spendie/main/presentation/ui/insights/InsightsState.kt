package com.ajeeb.spendie.main.presentation.ui.insights

import androidx.navigation.NavType
import kotlinx.serialization.Serializable
import kotlin.reflect.KType

@Serializable
data class InsightsState(
    val isLoading: Boolean = false
) {
    companion object {
        val typeMap: Map<KType, NavType<out Any>> = mapOf()
    }
}