package com.ajeeb.spendie.common.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ajeeb.spendie.main.presentation.ui.expense.ExpenseScreen
import com.ajeeb.spendie.main.presentation.ui.expense.ExpenseState
import com.ajeeb.spendie.main.presentation.ui.expense.ExpenseViewModel
import com.ajeeb.spendie.main.presentation.ui.main.MainScreen
import com.ajeeb.spendie.main.presentation.ui.main.MainState
import com.ajeeb.spendie.main.presentation.ui.main.MainViewModel


/**
 * NavGraph: This composable function defines the navigation graph for the application.
 *
 * It uses a [NavHost] to manage the navigation between different screens.
 * The navigation is based on the `State` classes ([MainState], [ExpenseState]) which represent
 * the different screens in the application.
 *
 * Each screen is associated with a corresponding ViewModel (e.g., [MainViewModel], [ExpenseViewModel])
 * which is retrieved using Hilt's `hiltViewModel` function.
 *
 * The `state`, `sideEffect`, and `onEvent` from the ViewModels are passed to the respective screen composables.
 *
 * Screens defined:
 *   - [MainState]: Represents the main screen of the application.
 *     - ViewModel: [MainViewModel]
 *     - Composable: [MainScreen]
 *   - [ExpenseState]: Represents the expense screen of the application.
 *     - ViewModel: [ExpenseViewModel]
 *     - Composable: [ExpenseScreen]
 *
 *  Navigation:
 *   - The [startDestination] is set to [MainState], meaning the main screen is the initial screen displayed.
 *   - To navigate between screens, you would call `navController.navigate(NewScreenState())` from within a screen composable.
 *     - Note: No navigation actions are present within this function. This Function only sets up the possible Navigations.
 */
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MainState()) {
        composable<MainState>(MainState.typeMap) {

            val vm = hiltViewModel<MainViewModel>()
            val state = vm.container.stateFlow.collectAsState()
            val sideEffect = vm.container.sideEffectFlow
            val onEvent = vm::onEvent

            MainScreen(state, sideEffect, onEvent) {
                navController.navigate(ExpenseState())
            }
        }

        composable<ExpenseState>(ExpenseState.typeMap) {

            val vm = hiltViewModel<ExpenseViewModel>()
            val state = vm.container.stateFlow.collectAsState()
            val sideEffect = vm.container.sideEffectFlow
            val onEvent = vm::onEvent

            ExpenseScreen(state, sideEffect, onEvent)
        }
    }
}