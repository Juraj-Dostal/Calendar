package sk.duri.calendar.ui.CreateEvent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.duri.calendar.ui.navigation.NavigationDestination
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import sk.duri.calendar.R
import sk.duri.calendar.data.TypUdalosti
import java.time.LocalTime
import java.util.Date

object CreateEventDestination : NavigationDestination {
    override val route = "createEvent"
}

@Composable
fun CreateEventScreen(
    //viewModel: CreateEventViewModel,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val corutineScope = rememberCoroutineScope()
    var notes by remember { mutableStateOf("") }
    var nameEvent by remember { mutableStateOf("")}
    var selectedFromDate by remember { mutableStateOf(Date()) }
    var selectedToDate by remember { mutableStateOf(Date()) }
    var selectedFromTime by remember { mutableStateOf(LocalTime.now()) }
    var selectedToTime by remember { mutableStateOf(LocalTime.now()) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(TypUdalosti.Udalost) }


    Column(Modifier.verticalScroll(rememberScrollState())) {
        OutlinedTextField(
            value = nameEvent,
            onValueChange = {nameEvent = it},
            modifier = modifier.align(Alignment.CenterHorizontally),
            label = { Text(stringResource(R.string.nameEvent)) }
        )
        Box(modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .align(Alignment.CenterHorizontally)
            .background(MaterialTheme.colorScheme.primary)
            .padding(10.dp)
        ) {
            Text(
                text = selectedOption.toString(),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.clickable(onClick = { expanded = true }))
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                TypUdalosti.values().forEach {
                    typ -> DropdownMenuItem(
                    text = { Text(typ.toString()) },
                        onClick = {
                            selectedOption = typ
                            expanded = false
                    }
                    )
                }
            }
        }
        Choose(
            selectedFromDate,
            selectedToDate,
            selectedFromTime,
            selectedToTime
        )
        Text(
            text = stringResource(R.string.notes),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(8.dp)
        )
        TextField(
            value = notes,
            onValueChange = {notes = it},
            modifier = modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.CenterHorizontally)
        )
        Row(
            modifier = modifier.align(Alignment.CenterHorizontally)
        ) {
            Button(
                onClick = {
                    /*viewModel.viewModelScope.launch {
                        viewModel.saveEvent(
                            nameEvent,
                            selectedFromDate,
                            selectedToDate,
                            selectedFromTime,
                            selectedToTime
                        )
                    }*/
                },
                modifier = modifier.padding(5.dp)
            ) {
                Text("Save")
            }
            Button(
                onClick = navigateBack,
                modifier = modifier.padding(5.dp)
            ) {
                Text("Cancel")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Choose(
    selectedFromDate: Date,
    selectedToDate: Date,
    selectedFromTime: LocalTime,
    selectedToTime: LocalTime,
    modifier: Modifier = Modifier
) {
    val fromTimeState = rememberTimePickerState()
    val toTimeState = rememberTimePickerState()
    val dateState = rememberDateRangePickerState()

    Column {
        DateRangePicker(
            state = dateState,
            modifier = modifier
                .padding(5.dp)
                .height(400.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .align(Alignment.CenterHorizontally)

        ) {
            Text(
                text = stringResource(R.string.startTime),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                style =  MaterialTheme.typography.bodyLarge,
                modifier = modifier.padding(end = 8.dp)
            )
            TimeInput(
                state = fromTimeState,
                modifier = modifier.padding(start = 8.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(R.string.endTime),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                style =  MaterialTheme.typography.bodyLarge,
                modifier = modifier.padding(end = 8.dp)
            )
            TimeInput(
                state = toTimeState,
                modifier = modifier.padding(start = 8.dp)
            )
        }
    }
    /*selectedFromDate = dateState.value?.start ?: Date()
    selectedToDate = dateState.value?.end ?: Date()
    selectedFromTime = LocalTime.of(fromTimeState.hour, fromTimeState.minute)
    selectedToTime = LocalTime.of(toTimeState.hour, toTimeState.minute)*/
}


@Preview(showBackground = true)
@Composable
fun PreviewCreateEventScreen() {
    CreateEventScreen(navigateBack = { /*Do nothing*/ })
}