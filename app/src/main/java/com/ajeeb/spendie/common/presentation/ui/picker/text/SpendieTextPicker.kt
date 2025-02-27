package com.ajeeb.bumblecar.common.presentation.ui.picker.date

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ajeeb.spendie.R

@Composable
fun SpendieTextPicker(
    modifier: Modifier = Modifier,
    placeholder: String,
    value: String?,
    isError: Boolean,
    onClick: () -> Unit
) {
    Row(modifier = modifier
        .clickable {
            onClick()
        }
        .background(MaterialTheme.colorScheme.background)
        .border(
            width = 1.dp, color = if (isError) {
                MaterialTheme.colorScheme.onError
            } else {
                MaterialTheme.colorScheme.surface
            }, shape = RoundedCornerShape(16.dp)
        )
        .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier.size(20.dp),
            painter = painterResource(R.drawable.ic_calendar),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
        )

        Spacer(Modifier.width(6.dp))

        Text(
            text = value ?: placeholder,
            style = MaterialTheme.typography.bodyMedium,
            color = if (value != null) {
                MaterialTheme.colorScheme.onBackground
            } else {
                MaterialTheme.colorScheme.secondary
            }
        )
    }

}