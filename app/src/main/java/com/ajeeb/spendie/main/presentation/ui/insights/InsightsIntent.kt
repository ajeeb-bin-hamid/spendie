package com.ajeeb.spendie.main.presentation.ui.insights

sealed class InsightsIntent {
    data object ToggleCurrency : InsightsIntent()
}