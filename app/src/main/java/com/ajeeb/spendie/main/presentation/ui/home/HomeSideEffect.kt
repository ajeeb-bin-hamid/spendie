package com.ajeeb.spendie.main.presentation.ui.home

sealed class HomeSideEffect {
    // Actions that can be performed on the UI 
    data class ShowToast(val message: String) : HomeSideEffect()
}