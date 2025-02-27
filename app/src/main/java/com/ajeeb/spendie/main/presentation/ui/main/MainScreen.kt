package com.ajeeb.spendie.main.presentation.ui.main

import android.widget.Toast
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ajeeb.spendie.R
import kotlinx.coroutines.flow.Flow

@Composable
fun MainScreen(
    state: State<MainState>,
    sideEffect: Flow<MainSideEffect>,
    onEvent: (MainIntent) -> Unit,
) {

    val context = LocalContext.current
    val mainNavController = rememberNavController()
    val mainNavBackStackEntry by mainNavController.currentBackStackEntryAsState()

    /**Collect SideEffects using Orbit*/
    LaunchedEffect(Unit) {
        sideEffect.collect { action ->
            when (action) {
                is MainSideEffect.ShowToast -> {
                    Toast.makeText(context, action.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.BottomCenter
        ) {

            val dividerColor = MaterialTheme.colorScheme.surface
            BottomNavigation(elevation = 0.dp,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .drawBehind {
                        val borderWidth = 1.dp.toPx() // Border thickness
                        drawLine(
                            color = dividerColor, // Border color
                            start = Offset(0f, 0f), // Starting point of the line
                            end = Offset(size.width, 0f), // Ending point of the line
                            strokeWidth = borderWidth
                        )
                    }) {
                BottomBarItem(
                    mainNavController = mainNavController,
                    mainNavBackStackEntry = mainNavBackStackEntry,
                    route = HOME,
                    selectedIcon = painterResource(R.drawable.ic_home_bold),
                    unselectedIcon = painterResource(R.drawable.ic_home),
                    contentDescription = stringResource(R.string.home)
                )

                BottomBarItem(
                    mainNavController = mainNavController,
                    mainNavBackStackEntry = mainNavBackStackEntry,
                    route = BUDGET,
                    selectedIcon = painterResource(R.drawable.ic_budget_bold),
                    unselectedIcon = painterResource(R.drawable.ic_budget),
                    contentDescription = stringResource(R.string.budgets)
                )

                BottomBarItem(
                    mainNavController = mainNavController,
                    mainNavBackStackEntry = mainNavBackStackEntry,
                    route = INSIGHTS,
                    selectedIcon = painterResource(R.drawable.ic_insights_bold),
                    unselectedIcon = painterResource(R.drawable.ic_insights),
                    contentDescription = stringResource(R.string.insights)
                )
            }

            //Main Content
            NavHost(
                navController = mainNavController,
                startDestination = HOME,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                modifier = Modifier.fillMaxSize()
            ) {
                composable(HOME) {

                }
            }
        }
    }
}

@Composable
private fun RowScope.BottomBarItem(
    mainNavController: NavController,
    mainNavBackStackEntry: NavBackStackEntry?,
    route: String,
    selectedIcon: Painter,
    unselectedIcon: Painter,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    val isSelected = mainNavBackStackEntry?.destination?.route == route
    BottomNavigationItem(
        selected = isSelected,
        icon = {
            Image(
                painter = if (isSelected) {
                    selectedIcon
                } else {
                    unselectedIcon
                },
                contentDescription = contentDescription,
                modifier = Modifier.align(Alignment.CenterVertically),
                colorFilter = ColorFilter.tint(androidx.compose.material.MaterialTheme.colors.onBackground)
            )

        },
        onClick = {
            mainNavController.navigate(route) {
                popUpTo(mainNavController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        modifier = modifier.background(androidx.compose.material.MaterialTheme.colors.background),
    )
}

const val HOME = "home"
const val BUDGET = "budget"
const val INSIGHTS = "insights"