package com.ajeeb.spendie.main.presentation.ui.expense

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ajeeb.spendie.R
import com.ajeeb.spendie.common.presentation.ui.text_field.basic.SpendieBasicTextField
import kotlinx.coroutines.flow.Flow

@Composable
fun ExpenseScreen(
    state: State<ExpenseState>,
    sideEffect: Flow<ExpenseSideEffect>,
    onEvent: (ExpenseIntent) -> Unit,
) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    /**Collect SideEffects using Orbit*/
    LaunchedEffect(Unit) {
        sideEffect.collect { action ->
            when (action) {
                is ExpenseSideEffect.ShowToast -> {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .verticalScroll(scrollState),
        ) {

            //Pick up TextField
            SpendieBasicTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                value = state.value.amount,
                label = stringResource(R.string.amount),
                placeholder = stringResource(R.string.enter_amount),
                isError = state.value.isErrorOnAmount,
                setOnValueChange = { newText ->
                    //
                },
                leadingIcon = {
                    Text("₹")
                })
        }
    }
}