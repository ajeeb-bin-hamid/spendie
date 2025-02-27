package com.ajeeb.spendie.main.presentation.ui.insights

import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ajeeb.spendie.R
import com.ajeeb.spendie.common.core.Bills
import com.ajeeb.spendie.common.core.Entertainment
import com.ajeeb.spendie.common.core.FoodAndBev
import com.ajeeb.spendie.common.core.Miscellaneous
import com.ajeeb.spendie.common.core.Transport
import com.ajeeb.spendie.main.domain.enums.CategoryType
import com.ajeeb.spendie.main.presentation.enums.CurrencyType
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.Pie
import kotlinx.coroutines.flow.Flow
import java.util.Locale

@Composable
fun InsightsScreen(
    state: State<InsightsState>,
    sideEffect: Flow<InsightsSideEffect>,
    onEvent: (InsightsIntent) -> Unit,
) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()

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
                },
                actions = {
                    Row(modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable {
                            onEvent(InsightsIntent.ToggleCurrency)
                        }) {
                        Text(
                            text = when (state.value.currencyType) {
                                CurrencyType.INR -> stringResource(R.string.inr)
                                CurrencyType.USD -> stringResource(R.string.usd)
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Image(
                            painter = painterResource(R.drawable.ic_arrow_down),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                        )
                    }
                })

            Spacer(Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {


                //Pie chart
                val totalSpends =
                    state.value.foodAndBevSpends + state.value.transportSpends + state.value.entertainmentSpends + state.value.billsSpends + state.value.miscellaneousSpends
                val pieList: ArrayList<Pie> = arrayListOf()
                val foodAndBev =
                    getPie(state.value.foodAndBevSpends, CategoryType.FOOD_AND_BEVERAGES)
                val transport = getPie(state.value.transportSpends, CategoryType.TRANSPORT)
                val entertainment =
                    getPie(state.value.entertainmentSpends, CategoryType.ENTERTAINMENT)
                val bills = getPie(state.value.billsSpends, CategoryType.BILLS)
                val miscellaneous =
                    getPie(state.value.miscellaneousSpends, CategoryType.MISCELLANEOUS)
                foodAndBev?.let { pieList.add(it) }
                transport?.let { pieList.add(it) }
                entertainment?.let { pieList.add(it) }
                bills?.let { pieList.add(it) }
                miscellaneous?.let { pieList.add(it) }

                SpendiePieChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp),
                    pieList = pieList,
                    totalSpends = totalSpends,
                    usdValue = state.value.usdValue,
                    currencyType = state.value.currencyType
                )

                Spacer(Modifier.height(24.dp))

                //Category break down
                CategoryBreakdown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    amount = state.value.foodAndBevSpends,
                    totalSpends = totalSpends,
                    categoryType = CategoryType.FOOD_AND_BEVERAGES,
                    usdValue = state.value.usdValue,
                    currencyType = state.value.currencyType
                )

                Spacer(Modifier.height(24.dp))

                CategoryBreakdown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    amount = state.value.transportSpends,
                    totalSpends = totalSpends,
                    categoryType = CategoryType.TRANSPORT,
                    usdValue = state.value.usdValue,
                    currencyType = state.value.currencyType
                )

                Spacer(Modifier.height(24.dp))

                CategoryBreakdown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    amount = state.value.entertainmentSpends,
                    totalSpends = totalSpends,
                    categoryType = CategoryType.ENTERTAINMENT,
                    usdValue = state.value.usdValue,
                    currencyType = state.value.currencyType
                )

                Spacer(Modifier.height(24.dp))

                CategoryBreakdown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    amount = state.value.billsSpends,
                    totalSpends = totalSpends,
                    categoryType = CategoryType.BILLS,
                    usdValue = state.value.usdValue,
                    currencyType = state.value.currencyType
                )

                Spacer(Modifier.height(24.dp))

                CategoryBreakdown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    amount = state.value.miscellaneousSpends,
                    totalSpends = totalSpends,
                    categoryType = CategoryType.MISCELLANEOUS,
                    usdValue = state.value.usdValue,
                    currencyType = state.value.currencyType
                )

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun SpendiePieChart(
    modifier: Modifier = Modifier,
    pieList: ArrayList<Pie>,
    totalSpends: Double,
    usdValue: Double?,
    currencyType: CurrencyType
) {
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

        val usdAmount = if (usdValue != null) {
            String.format(Locale.US, "%.2f", totalSpends * usdValue)
        } else {
            "0"
        }

        Text(
            text = when (currencyType) {
                CurrencyType.INR -> stringResource(R.string.inr_value, totalSpends)
                CurrencyType.USD -> stringResource(R.string.usd_value, usdAmount)
            },
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

@Composable
fun CategoryBreakdown(
    modifier: Modifier = Modifier,
    amount: Double,
    totalSpends: Double,
    categoryType: CategoryType,
    usdValue: Double?,
    currencyType: CurrencyType
) {

    val backgroundColor = when (categoryType) {
        CategoryType.FOOD_AND_BEVERAGES -> FoodAndBev
        CategoryType.TRANSPORT -> Transport
        CategoryType.ENTERTAINMENT -> Entertainment
        CategoryType.BILLS -> Bills
        CategoryType.MISCELLANEOUS -> Miscellaneous
    }

    val icon = when (categoryType) {
        CategoryType.FOOD_AND_BEVERAGES -> painterResource(R.drawable.ic_food_bold)
        CategoryType.TRANSPORT -> painterResource(R.drawable.ic_transport_bold)
        CategoryType.ENTERTAINMENT -> painterResource(R.drawable.ic_entertainment_bold)
        CategoryType.BILLS -> painterResource(R.drawable.ic_bills_bold)
        CategoryType.MISCELLANEOUS -> painterResource(R.drawable.ic_miscellaneous_bold)
    }

    val categoryName = when (categoryType) {
        CategoryType.FOOD_AND_BEVERAGES -> stringResource(R.string.food_beverages)
        CategoryType.TRANSPORT -> stringResource(R.string.transport)
        CategoryType.ENTERTAINMENT -> stringResource(R.string.entertainment)
        CategoryType.BILLS -> stringResource(R.string.bills)
        CategoryType.MISCELLANEOUS -> stringResource(R.string.miscellaneous)
    }

    val usdAmount = if (usdValue != null) {
        String.format(Locale.US, "%.2f", amount * usdValue)
    } else {
        "0"
    }

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(58.dp)
                .clip(CircleShape)
                .background(backgroundColor.copy(alpha = 0.1f)), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = icon,
                contentDescription = null,
                colorFilter = ColorFilter.tint(backgroundColor)
            )
        }

        Spacer(Modifier.width(12.dp))

        //Progress bar and text
        val progress = if (totalSpends != 0.0) {
            (amount / totalSpends).coerceIn(0.0, 1.0).toFloat()
        } else {
            0f
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = categoryName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(12.dp))


            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = backgroundColor,
                backgroundColor = backgroundColor.copy(alpha = 0.1f),
            )
        }

        //Amount
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = when (currencyType) {
                    CurrencyType.INR -> stringResource(R.string.inr_value, amount)
                    CurrencyType.USD -> stringResource(R.string.usd_value, usdAmount)
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "${String.format(Locale.US, "%.2f", progress * 100)}%",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}