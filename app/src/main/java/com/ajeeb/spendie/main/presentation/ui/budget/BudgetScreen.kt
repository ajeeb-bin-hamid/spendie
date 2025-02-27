package com.ajeeb.spendie.main.presentation.ui.budget

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajeeb.spendie.R
import com.ajeeb.spendie.common.core.Bills
import com.ajeeb.spendie.common.core.Entertainment
import com.ajeeb.spendie.common.core.FoodAndBev
import com.ajeeb.spendie.common.core.Miscellaneous
import com.ajeeb.spendie.common.core.Transport
import com.ajeeb.spendie.common.core.poppinsFontFamily
import com.ajeeb.spendie.main.domain.enums.CategoryType
import kotlinx.coroutines.flow.Flow

@Composable
fun BudgetScreen(
    state: State<BudgetState>,
    sideEffect: Flow<BudgetSideEffect>,
    onEvent: (BudgetIntent) -> Unit,
) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    /**Collect SideEffects using Orbit*/
    LaunchedEffect(Unit) {
        sideEffect.collect { action ->
            when (action) {
                is BudgetSideEffect.ShowToast -> {
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
                        text = stringResource(R.string.my_budget),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                })

            Spacer(Modifier.height(16.dp))

            //Main content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState)
            ) {
                BudgetCard(
                    modifier = Modifier.fillMaxWidth(),
                    categoryType = CategoryType.FOOD_AND_BEVERAGES,
                    spends = 2000.0,
                    budget = null
                )
            }
        }
    }
}

@Composable
private fun BudgetCard(
    modifier: Modifier = Modifier, categoryType: CategoryType, spends: Double, budget: Double?
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


    val percentage = if (budget != null) (spends / budget) * 100 else null
    val alertMessage = when {
        percentage == null -> stringResource(R.string.no_budget_set)
        percentage < 50 -> stringResource(R.string.spending_is_low)
        percentage in 50.0..79.99 -> stringResource(R.string.keep_an_eye_on_it)
        percentage in 80.0..99.99 -> stringResource(R.string.almost_at_the_limit)
        percentage >= 100 -> stringResource(R.string.over_budget)
        else -> stringResource(R.string.no_budget_set)
    }

    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(58.dp)
                        .clip(CircleShape)
                        .background(backgroundColor.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = icon,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(backgroundColor)
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = categoryName,
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = alertMessage,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            Spacer(Modifier.width(12.dp))

            IconButton(onClick = { }) {
                Image(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        //Spends & Budget
        Row(modifier = Modifier.fillMaxWidth()) {
            AmountCard(
                title = stringResource(R.string.total_spends), value = stringResource(
                    R.string.amount_value, spends
                )
            )

            Spacer(Modifier.width(24.dp))

            AmountCard(
                title = stringResource(R.string.total_budget), value = if (budget != null) {
                    stringResource(R.string.amount_value, budget)
                } else {
                    stringResource(R.string.infinity_symbol)
                }
            )
        }

        Spacer(Modifier.height(16.dp))

        //Progress bar
        val progress = if (budget != null) {
            (spends / budget).coerceIn(0.0, 1.0).toFloat()
        } else {
            0f
        }
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
}

@Composable
fun AmountCard(modifier: Modifier = Modifier, title: String, value: String) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = value,
            fontFamily = poppinsFontFamily(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}