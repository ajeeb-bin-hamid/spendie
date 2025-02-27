package com.ajeeb.spendie.main.presentation.ui.insights

import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ajeeb.spendie.R
import com.ajeeb.spendie.common.core.Bills
import com.ajeeb.spendie.common.core.Entertainment
import com.ajeeb.spendie.common.core.FoodAndBev
import com.ajeeb.spendie.common.core.Miscellaneous
import com.ajeeb.spendie.common.core.Transport
import com.ajeeb.spendie.main.domain.enums.CategoryType
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.Pie
import kotlinx.coroutines.flow.Flow

@Composable
fun InsightsScreen(
    state: State<InsightsState>,
    sideEffect: Flow<InsightsSideEffect>,
    onEvent: (InsightsIntent) -> Unit,
) {

    val context = LocalContext.current

    /**Collect SideEffects using Orbit*/
    LaunchedEffect(Unit) {
        sideEffect.collect { action ->
            when (action) {
                is InsightsSideEffect.ShowToast -> {
                    Toast.makeText(context, action.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding),
        ) {

            //Top bar
            TopAppBar(backgroundColor = MaterialTheme.colorScheme.background,
                elevation = 0.dp,
                title = {
                    Text(
                        text = stringResource(R.string.insights),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                })

            Spacer(Modifier.height(32.dp))

            val totalSpends =
                state.value.foodAndBevSpends + state.value.transportSpends + state.value.entertainmentSpends + state.value.billsSpends + state.value.miscellaneousSpends
            val pieList: ArrayList<Pie> = arrayListOf()
            val foodAndBev = getPie(state.value.foodAndBevSpends, CategoryType.FOOD_AND_BEVERAGES)
            val transport = getPie(state.value.transportSpends, CategoryType.TRANSPORT)
            val entertainment = getPie(state.value.entertainmentSpends, CategoryType.ENTERTAINMENT)
            val bills = getPie(state.value.billsSpends, CategoryType.BILLS)
            val miscellaneous = getPie(state.value.miscellaneousSpends, CategoryType.MISCELLANEOUS)
            foodAndBev?.let { pieList.add(it) }
            transport?.let { pieList.add(it) }
            entertainment?.let { pieList.add(it) }
            bills?.let { pieList.add(it) }
            miscellaneous?.let { pieList.add(it) }

            SpendiePieChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp), pieList = pieList,
                totalSpends = totalSpends
            )
        }
    }
}

@Composable
fun SpendiePieChart(modifier: Modifier = Modifier, pieList: ArrayList<Pie>, totalSpends: Double) {
    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        PieChart(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            data = pieList,
            selectedScale = 1.2f,
            scaleAnimEnterSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
            ),
            colorAnimEnterSpec = tween(300),
            colorAnimExitSpec = tween(300),
            scaleAnimExitSpec = tween(300),
            spaceDegreeAnimExitSpec = tween(300),
            style = Pie.Style.Stroke(width = 56.dp),
        )

        Text(
            text = stringResource(R.string.amount_value, totalSpends),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun getPie(amount: Double, categoryType: CategoryType): Pie? {

    val backgroundColor = when (categoryType) {
        CategoryType.FOOD_AND_BEVERAGES -> FoodAndBev
        CategoryType.TRANSPORT -> Transport
        CategoryType.ENTERTAINMENT -> Entertainment
        CategoryType.BILLS -> Bills
        CategoryType.MISCELLANEOUS -> Miscellaneous
    }

    val categoryName = when (categoryType) {
        CategoryType.FOOD_AND_BEVERAGES -> stringResource(R.string.food_beverages)
        CategoryType.TRANSPORT -> stringResource(R.string.transport)
        CategoryType.ENTERTAINMENT -> stringResource(R.string.entertainment)
        CategoryType.BILLS -> stringResource(R.string.bills)
        CategoryType.MISCELLANEOUS -> stringResource(R.string.miscellaneous)
    }

    return if (amount > 0.0) {
        Pie(
            label = categoryName,
            data = amount,
            color = backgroundColor,
            selectedColor = backgroundColor
        )
    } else {
        null
    }
}