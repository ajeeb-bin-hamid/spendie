package com.ajeeb.spendie.main.presentation.ui.main

sealed class MainSideEffect {
    // Actions that can be performed on the UI 
    data class ShowToast(val message: String) : MainSideEffect()
}