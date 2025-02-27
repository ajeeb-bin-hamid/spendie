package com.ajeeb.spendie.main.presentation.ui.expense.choose_category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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

@Composable
fun ChooseCategorySheet(modifier: Modifier = Modifier, onClickItem: (CategoryType) -> Unit) {

    val scrollState = rememberScrollState()

    /**Design the UI here*/
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .navigationBarsPadding()
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        //Indicator
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.surface)

        )
        Spacer(modifier = Modifier.height(16.dp))
        //Title
        Text(
            text = stringResource(R.string.choose_category),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(4.dp))
        //Sub text
        Text(
            text = stringResource(R.string.choose_a_category_for_your_expense),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider(
            thickness = 1.dp, color = MaterialTheme.colorScheme.surface
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
        ) {

            Spacer(Modifier.height(24.dp))

            CategoryItem(modifier = Modifier.fillMaxWidth(),
                backgroundColor = FoodAndBev.copy(alpha = 0.1f),
                title = stringResource(R.string.food_beverages),
                onClick = {
                    onClickItem(CategoryType.FOOD_AND_BEVERAGES)
                }) {
                Image(
                    painter = painterResource(R.drawable.ic_food_bold),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(FoodAndBev)
                )
            }

            Spacer(Modifier.height(24.dp))

            CategoryItem(modifier = Modifier.fillMaxWidth(),
                backgroundColor = Transport.copy(alpha = 0.1f),
                title = stringResource(R.string.transport),
                onClick = {
                    onClickItem(CategoryType.TRANSPORT)
                }) {
                Image(
                    painter = painterResource(R.drawable.ic_transport_bold),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Transport)
                )
            }

            Spacer(Modifier.height(24.dp))

            CategoryItem(modifier = Modifier.fillMaxWidth(),
                backgroundColor = Entertainment.copy(alpha = 0.1f),
                title = stringResource(R.string.entertainment),
                onClick = {
                    onClickItem(CategoryType.ENTERTAINMENT)
                }) {
                Image(
                    painter = painterResource(R.drawable.ic_entertainment_bold),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Entertainment)
                )
            }

            Spacer(Modifier.height(24.dp))

            CategoryItem(modifier = Modifier.fillMaxWidth(),
                backgroundColor = Bills.copy(alpha = 0.1f),
                title = stringResource(R.string.bills),
                onClick = {
                    onClickItem(CategoryType.BILLS)
                }) {
                Image(
                    painter = painterResource(R.drawable.ic_bills_bold),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Bills)
                )
            }

            Spacer(Modifier.height(24.dp))

            CategoryItem(modifier = Modifier.fillMaxWidth(),
                backgroundColor = Miscellaneous.copy(alpha = 0.1f),
                title = stringResource(R.string.miscellaneous),
                onClick = {
                    onClickItem(CategoryType.MISCELLANEOUS)
                }) {
                Image(
                    painter = painterResource(R.drawable.ic_miscellaneous_bold),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Miscellaneous)
                )
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun CategoryItem(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    title: String,
    onClick: () -> Unit,
    icon: @Composable () -> Unit
) {
    Row(modifier = modifier.clickable {
        onClick()
    }, verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }

        Spacer(Modifier.width(12.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}