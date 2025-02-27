package com.ajeeb.spendie.main.presentation.ui.insights

sealed class InsightsSideEffect {
    // Actions that can be performed on the UI 
    data class ShowToast(val message: String) : InsightsSideEffect()
}