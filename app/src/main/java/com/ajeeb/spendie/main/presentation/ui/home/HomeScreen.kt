package com.ajeeb.spendie.main.presentation.ui.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.ajeeb.spendie.common.core.parseDate
import com.ajeeb.spendie.common.core.poppinsFontFamily
import com.ajeeb.spendie.common.domain.model.Expense
import com.ajeeb.spendie.main.domain.enums.CategoryType
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen(
    state: State<HomeState>,
    sideEffect: Flow<HomeSideEffect>,
    onEvent: (HomeIntent) -> Unit,
    navigateToExpenseScreen: (Expense?) -> Unit
) {

    val context = LocalContext.current

    /**Collect SideEffects using Orbit*/
    LaunchedEffect(Unit) {
        sideEffect.collect { action ->
            when (action) {
                is HomeSideEffect.ShowToast -> {
                    Toast.makeText(context, action.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .safeDrawingPadding(), floatingActionButton = {
        HomeFAB {
            navigateToExpenseScreen(null)
        }
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding),
        ) {

            //Top bar
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.my_expenses),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },

                actions = {
                    IconButton(onClick = { }) {
                        Image(
                            painter = painterResource(R.drawable.ic_arrow_left),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                        )
                    }
                },

                backgroundColor = MaterialTheme.colorScheme.background, elevation = 0.dp,
            )

            Spacer(Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                itemsIndexed(items = state.value.expenses) { _, expense ->
                    ExpenseItem(modifier = Modifier.padding(bottom = 16.dp), expense = expense) {
                        navigateToExpenseScreen(expense)
                    }
                }
            }
        }
    }
}

@Composable
fun HomeFAB(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Expense")
    }
}

@Composable
fun ExpenseItem(modifier: Modifier = Modifier, expense: Expense, onClick: () -> Unit) {

    val backgroundColor = when (expense.category) {
        CategoryType.FOOD_AND_BEVERAGES -> FoodAndBev
        CategoryType.TRANSPORT -> Transport
        CategoryType.ENTERTAINMENT -> Entertainment
        CategoryType.BILLS -> Bills
        CategoryType.MISCELLANEOUS -> Miscellaneous
    }

    val icon = when (expense.category) {
        CategoryType.FOOD_AND_BEVERAGES -> painterResource(R.drawable.ic_food_bold)
        CategoryType.TRANSPORT -> painterResource(R.drawable.ic_transport_bold)
        CategoryType.ENTERTAINMENT -> painterResource(R.drawable.ic_entertainment_bold)
        CategoryType.BILLS -> painterResource(R.drawable.ic_bills_bold)
        CategoryType.MISCELLANEOUS -> painterResource(R.drawable.ic_miscellaneous_bold)
    }
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier
        .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(16.dp)
        )
        .clickable {
            onClick()
        }
        .padding(16.dp)
    ) {

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

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = expense.notes,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = expense.date.parseDate("MMM dd, yyyy"),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Spacer(Modifier.width(12.dp))

        //Amount
        Text(
            text = "₹${expense.amount}",
            fontFamily = poppinsFontFamily(),
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

    }
}