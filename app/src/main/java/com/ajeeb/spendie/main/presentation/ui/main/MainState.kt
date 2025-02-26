package com.ajeeb.spendie.main.presentation.ui.main

import androidx.navigation.NavType
import kotlinx.serialization.Serializable
import kotlin.reflect.KType

@Serializable
data class MainState(
    val isLoading: Boolean = false,

) {
    companion object {
        val typeMap: Map<KType, NavType<out Any>> = mapOf()
    }
}