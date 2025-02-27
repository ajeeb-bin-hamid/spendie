package com.ajeeb.spendie.main.presentation.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel(), ContainerHost<MainState, MainSideEffect> {

    private val initialState = savedStateHandle.toRoute<MainState>(MainState.typeMap)
    override val container = viewModelScope.container<MainState, MainSideEffect>(initialState)


    fun onEvent(event: MainIntent) {
        //
    }
}