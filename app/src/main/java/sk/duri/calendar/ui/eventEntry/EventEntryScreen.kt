package sk.duri.calendar.ui.eventEntry

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.CalendarTheme
import kotlinx.coroutines.launch
import sk.duri.calendar.R
import sk.duri.calendar.data.TypUdalosti
import sk.duri.calendar.ui.AppViewModelProvider
import sk.duri.calendar.ui.navigation.NavigationDestination

object EventEntryDestination : NavigationDestination {
    override val route = "eventEntry"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventEntryScreen(
    navigateToNameDayEdit: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: EventEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val corutineScope = rememberCoroutineScope()

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Calendar")
                },
                navigationIcon = {
                    IconButton(onClick = navigateToNameDayEdit ) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                },
                actions = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Filled.DateRange, contentDescription = "Change screen to MonthCalendar")
                    }
                }
            )
        },
    ){
            innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            EventEntryBody(
                udalostDetails = viewModel.udalostUiState.udalostDetails,
                onUdalostValueChange = viewModel::updateUiState,
                navigateBack = navigateBack,
                onSaveClick = {
                    corutineScope.launch {
                        viewModel.saveUdalost()
                        navigateBack()
                    }
                }
            )
        }
    }


}

@Composable
fun EventEntryBody(
    udalostDetails: UdalostDetails,
    onUdalostValueChange: (UdalostDetails) -> Unit,
    navigateBack: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = udalostDetails.nazov,
            onValueChange = { onUdalostValueChange( udalostDetails.copy(nazov = it) )},
            label = { Text(text = stringResource(R.string.nameEvent)) },
            modifier = modifier.align(Alignment.CenterHorizontally),
            )
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.TopStart)
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
                .height(30.dp)
                .background(MaterialTheme.colorScheme.primary)

        ) {
            Text(
                text = udalostDetails.typ.nazov,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable(onClick = { expanded = true })
                    .fillMaxWidth()
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)

            ) {
                TypUdalosti.entries.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it.nazov) },
                        onClick = {
                            onUdalostValueChange(udalostDetails.copy(typ = it))
                            expanded = false
                        }
                    )
                }
            }
        }
        FromDateInput(
            udalostDetails,
            onUdalostValueChange,
            modifier.align(Alignment.CenterHorizontally)
        )
        ToDateInput(
            udalostDetails,
            onUdalostValueChange,
            modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = stringResource(R.string.notes),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)

        )
        OutlinedTextField(
            value = udalostDetails.poznamka,
            onValueChange = { onUdalostValueChange(udalostDetails.copy(poznamka = it)) },
            modifier = modifier
                .align(Alignment.CenterHorizontally),
        )
        Row(
            modifier = modifier.align(Alignment.CenterHorizontally)
        ) {
            Button(
                onClick = onSaveClick,
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

@Composable
fun FromDateInput(
    udalostDetails: UdalostDetails,
    onUdalostValueChange: (UdalostDetails) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {

        Text(
            text = stringResource(R.string.from),
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
        )
        Row {
            Text(
                text = stringResource(R.string.time),
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 10.dp)
            )
            OutlinedTextField(
                value = udalostDetails.odHodina,
                onValueChange = { onUdalostValueChange(udalostDetails.copy( odHodina = it )) },
                label = { Text(text = stringResource(R.string.hour)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
                modifier = Modifier
                    .width(80.dp)
            )
            Text(
                text = ":",
                fontSize = 40.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            OutlinedTextField(
                value = udalostDetails.odMinuta,
                onValueChange = { onUdalostValueChange(udalostDetails.copy( odMinuta = it ))},
                label = { Text(text = stringResource(R.string.minute)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
                modifier = modifier
                    .width(80.dp)
            )
        }
        Row {
            Text(
                text = stringResource(R.string.date),
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 10.dp)
            )
            OutlinedTextField(
                value = udalostDetails.odDen,
                onValueChange =  { onUdalostValueChange(udalostDetails.copy( odDen = it ))},
                label = { Text(text = stringResource(R.string.day)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
                modifier = modifier
                    .width(60.dp)
            )
            Text(
                text = ".",
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = modifier
                    .align(Alignment.Bottom)
            )
            OutlinedTextField(
                value = udalostDetails.odMesiac,
                onValueChange =  { onUdalostValueChange(udalostDetails.copy( odMesiac = it ))},
                label = { Text(text = stringResource(R.string.month)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
                modifier = modifier
                    .width(80.dp)
            )
            Text(
                text = ".",
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = modifier
                    .align(Alignment.Bottom)
            )
            OutlinedTextField(
                value = udalostDetails.odRok,
                onValueChange = { onUdalostValueChange(udalostDetails.copy( odRok = it ))},
                label = { Text(text = stringResource(R.string.year)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
                modifier = modifier
                    .width(80.dp)
            )
        }
    }
}

@Composable
fun ToDateInput(
    udalostDetails: UdalostDetails,
    onUdalostValueChange: (UdalostDetails) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {

        Text(
            text = stringResource(R.string.to),
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
        )
        Row {
            Text(
                text = stringResource(R.string.time),
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 10.dp)
            )
            OutlinedTextField(
                value = udalostDetails.doHodina,
                onValueChange = { onUdalostValueChange(udalostDetails.copy( doHodina = it )) },
                label = { Text(text = stringResource(R.string.hour)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
                modifier = Modifier
                    .width(80.dp)
            )
            Text(
                text = ":",
                fontSize = 40.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            OutlinedTextField(
                value = udalostDetails.doMinuta,
                onValueChange = { onUdalostValueChange(udalostDetails.copy( doMinuta = it ))},
                label = { Text(text = stringResource(R.string.minute)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
                modifier = modifier
                    .width(80.dp)
            )
        }
        Row {
            Text(
                text = stringResource(R.string.date),
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 10.dp)
            )
            OutlinedTextField(
                value = udalostDetails.doDen,
                onValueChange =  { onUdalostValueChange(udalostDetails.copy( doDen = it ))},
                label = { Text(text = stringResource(R.string.day)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
                modifier = modifier
                    .width(60.dp)
            )
            Text(
                text = ".",
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = modifier
                    .align(Alignment.Bottom)
            )
            OutlinedTextField(
                value = udalostDetails.doMesiac,
                onValueChange =  { onUdalostValueChange(udalostDetails.copy( doMesiac = it ))},
                label = { Text(text = stringResource(R.string.month)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
                modifier = modifier
                    .width(80.dp)
            )
            Text(
                text = ".",
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = modifier
                    .align(Alignment.Bottom)
            )
            OutlinedTextField(
                value = udalostDetails.doRok,
                onValueChange = { onUdalostValueChange(udalostDetails.copy( doRok = it ))},
                label = { Text(text = stringResource(R.string.year)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
                modifier = modifier
                    .width(80.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventEntryScreenPreview() {
    CalendarTheme {
        EventEntryBody(
            udalostDetails = UdalostDetails(1, "Event", "0", "13", "1", "1", "2022", "30", "13", "1", "1", "2022", "Note", TypUdalosti.Udalost),
            onUdalostValueChange = { /*TODO*/ },
            navigateBack = { /*TODO*/ },
            onSaveClick = { /*TODO*/ })
    }
}