package com.ajeeb.spendie.main.presentation.ui.expense

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ajeeb.bumblecar.common.presentation.ui.picker.date.SpendieTextPicker
import com.ajeeb.spendie.R
import com.ajeeb.spendie.common.core.parseDate
import com.ajeeb.spendie.common.core.toUtcString
import com.ajeeb.spendie.common.presentation.ui.text_field.basic.SpendieBasicTextField
import com.ajeeb.spendie.main.domain.enums.CategoryType
import com.ajeeb.spendie.main.presentation.ui.expense.choose_category.ChooseCategorySheet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun ExpenseScreen(
    state: State<ExpenseState>,
    sideEffect: Flow<ExpenseSideEffect>,
    onEvent: (ExpenseIntent) -> Unit,
    navigateBack: () -> Unit
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val keyboardController = LocalSoftwareKeyboardController.current

    // Callback to close the bottom sheet.
    val closeSheet = { coroutineScope.launch { sheetState.hide() } }

    // Callback to open the bottom sheet.
    val openSheet = {
        keyboardController?.hide()
        coroutineScope.launch { sheetState.show() }
    }

    BackHandler(enabled = sheetState.isVisible) {
        closeSheet()
    }

    /**Collect SideEffects using Orbit*/
    LaunchedEffect(Unit) {
        sideEffect.collect { action ->
            when (action) {
                is ExpenseSideEffect.ShowToast -> {
                    Toast.makeText(context, action.message, Toast.LENGTH_SHORT).show()
                }

                is ExpenseSideEffect.ExpenseSaved -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.expense_saved_successfully),
                        Toast.LENGTH_SHORT
                    ).show()
                    navigateBack()
                }
            }
        }
    }

    ModalBottomSheetLayout(sheetState = sheetState,
        modifier = Modifier,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContent = {
            ChooseCategorySheet {
                onEvent(ExpenseIntent.SetCategory(it))
                closeSheet()
            }
        }) {

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
                    .padding(padding),
            ) {

                TopAppBar(title = {
                    Text(
                        text = stringResource(R.string.expense),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }, navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Image(
                            painter = painterResource(R.drawable.ic_arrow_left),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                        )
                    }

                }, backgroundColor = MaterialTheme.colorScheme.background, elevation = 0.dp)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                ) {
                    //Amount TextField
                    SpendieBasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        value = state.value.amount,
                        label = stringResource(R.string.amount),
                        placeholder = stringResource(R.string.enter_amount),
                        isError = state.value.isErrorOnAmount,
                        setOnValueChange = { newText ->
                            onEvent(ExpenseIntent.SetAmount(newText))
                        },
                        leadingIcon = {
                            Text(stringResource(R.string.rupee_symbol))
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                        )
                    )

                    Spacer(Modifier.height(12.dp))

                    //Category`
                    val category = when (state.value.category) {
                        CategoryType.FOOD_AND_BEVERAGES -> stringResource(R.string.food_beverages)
                        CategoryType.TRANSPORT -> stringResource(R.string.transport)
                        CategoryType.ENTERTAINMENT -> stringResource(R.string.entertainment)
                        CategoryType.BILLS -> stringResource(R.string.bills)
                        CategoryType.MISCELLANEOUS -> stringResource(R.string.miscellaneous)
                        null -> null
                    }
                    SpendieTextPicker(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                        placeholder = stringResource(R.string.category),
                        value = category,
                        isError = state.value.isErrorOnCategory,
                        onClick = {
                            openSheet()
                        },
                        isArrowVisible = true,
                        leadingIcon = {
                            Image(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(R.drawable.ic_filter),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                            )
                        })

                    Spacer(Modifier.height(12.dp))

                    //Date
                    SpendieTextPicker(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                        placeholder = stringResource(R.string.date),
                        value = state.value.date.parseDate("MMM dd, yyyy"),
                        isError = state.value.isErrorOnDate,
                        onClick = {
                            openDatePicker(context) {
                                onEvent(ExpenseIntent.SetDate(it))
                            }
                        },
                        isArrowVisible = false,
                        leadingIcon = {
                            Image(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(R.drawable.ic_calendar),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                            )
                        })

                    Spacer(Modifier.height(4.dp))

                    //Notes TextField
                    SpendieBasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        value = state.value.notes ?: "",
                        label = stringResource(R.string.notes),
                        placeholder = stringResource(R.string.enter_notes),
                        isError = false,
                        setOnValueChange = { newText ->
                            onEvent(ExpenseIntent.SetNotes(newText))
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Done
                        )
                    )

                    Spacer(Modifier.height(20.dp))

                    //Save Button
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        onClick = {
                            onEvent(ExpenseIntent.SaveExpense)
                        },
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.save_expense),
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}


private fun openDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val cal = Calendar.getInstance()
    val calYear = cal[Calendar.YEAR]
    val calMonth = cal[Calendar.MONTH]
    val calDay = cal[Calendar.DAY_OF_MONTH]

    val dpd = DatePickerDialog(context, { _, year, monthOfYear, dayOfMonth ->

        val date = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, monthOfYear)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
            set(Calendar.HOUR, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        onDateSelected(date.toUtcString())

    }, calYear, calMonth, calDay)
    dpd.show()
}