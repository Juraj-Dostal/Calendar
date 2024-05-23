package sk.duri.calendar.ui.CreateEvent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.duri.calendar.ui.navigation.NavigationDestination
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import sk.duri.calendar.R

object CreateEventDestination : NavigationDestination {
    override val route = "createEvent"
}

@Composable
fun CreateEventScreen(
    modifier: Modifier = Modifier
) {
    var nameEvent = ""


    Column {
        OutlinedTextField(
            value = nameEvent,
            onValueChange = {nameEvent = it},
            modifier = modifier.align(Alignment.CenterHorizontally),
            label = { Text(stringResource(R.string.nameEvent)) }
        )
        Choose()
        Row(
            modifier = modifier.align(Alignment.CenterHorizontally)
        ) {
            Button(
                onClick = {   },
                modifier = modifier.padding(5.dp)
            ) {
                Text("Save")
            }
            Button(
                onClick = { /*TODO*/ },
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
                .size(400.dp, 250.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
        TimeInput(
            state = fromTimeState,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
        )
        TimeInput(
            state = toTimeState,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCreateEventScreen() {
    CreateEventScreen()
}