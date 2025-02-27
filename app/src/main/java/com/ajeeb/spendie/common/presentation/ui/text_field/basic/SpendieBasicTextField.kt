package com.ajeeb.spendie.common.presentation.ui.text_field.basic

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SpendieBasicTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    placeholder: String,
    isError: Boolean,
    setOnValueChange: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    var isFocused by remember { mutableStateOf(false) }
    val labelSize by animateFloatAsState(
        if (isFocused || value.isNotEmpty()) 12f else 16f, label = ""
    )

    OutlinedTextField(modifier = modifier.onFocusChanged { focus ->
        isFocused = focus.isFocused
    },
        value = value,
        maxLines = 1,
        isError = isError,
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Sentences, imeAction = ImeAction.Done
        ),
        label = {
            Text(
                text = label,
                fontSize = labelSize.sp,
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        leadingIcon = leadingIcon,
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
            unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            errorLabelColor = MaterialTheme.colorScheme.secondary,
            errorContainerColor = MaterialTheme.colorScheme.background,
            errorIndicatorColor = MaterialTheme.colorScheme.onError
        ),
        onValueChange = {
            setOnValueChange(it)
        })
}
