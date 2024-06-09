package sk.duri.calendar.ui.dayCalendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import sk.duri.calendar.R
import sk.duri.calendar.data.TypUdalosti
import sk.duri.calendar.data.Udalost
import sk.duri.calendar.ui.AppViewModelProvider
import sk.duri.calendar.ui.navigation.NavigationDestination

object DayCalendarDestination : NavigationDestination {
    override val route = "dayCalendar"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayCalendarScreen(
    navigateToEventEntry: () -> Unit,
    navigateToMonthCalendar: () -> Unit,
    navigateToNameDayEdit: () -> Unit,
    navigateToEventEdit: (Int) -> Unit,
    viewModel: DayCalendarViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val dayCalendarUiState by viewModel.dayCalendarUiState.collectAsState()
    var mesiac = 0
    var den = 0

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(R.string.app_name))
                },
                navigationIcon = {
                    IconButton(onClick = navigateToNameDayEdit ) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                },
                actions = {
                    IconButton(onClick = navigateToMonthCalendar) {
                        Icon(Icons.Filled.DateRange, contentDescription = "Change screen to MonthCalendar")
                    }

                }
            )
        },
        floatingActionButton =  {
            FloatingActionButton(
                onClick = { navigateToEventEntry() },
            ) { Icon(Icons.Filled.Add, contentDescription = "Add") }
        }
    ){
            innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            items(
                items = dayCalendarUiState.udalostZoznam,
                key = {it.id}
            ){
                if (mesiac != it.odMesiac || den != it.odDen){
                    mesiac = it.odMesiac
                    den = it.odDen
                    DayHeader(it.odDen, it.odMesiac, it.odRok)
                }
                Event(
                    event = it,
                    onDelete = { viewModel.deleteUdalost(it) },
                    onEdit = navigateToEventEdit
                )
            }

        }
    }
}

@Composable
fun Event(
    event: Udalost,
    onDelete: () -> Unit,
    onEdit: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.padding(5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
        ) {
            Row {
                Text(
                    text = event.nazov,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDelete() }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete")
                }
                IconButton(onClick = { onEdit(event.id) }) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit")
                }
            }
            Text(
                text = event.typ.nazov,
                style = MaterialTheme.typography.bodyLarge
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.from).uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = stringResource(R.string.to).uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${event.odDen}.${event.odMesiac}.${event.odRok}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "${event.doDen}.${event.doMesiac}.${event.doRok}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = String.format("%02d:%02d", event.odHodina, event.odMinuta),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = String.format("%02d:%02d", event.doHodina, event.doMinuta),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Text(
                text = stringResource(R.string.notes).uppercase(),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = event.poznamka,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun DayHeader(
    day: Int,
    month: Int,
    year: Int,
    modifier: Modifier = Modifier
){
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
        ) {
            Text(
                text = "$day.$month.$year",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Preview( showBackground = true)
@Composable
fun DayCalendarScreenPreview() {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        DayHeader(24, 5, 2024)
        Event(Udalost(1, "Skuska INF2", 0,9, 24, 5, 2024,11,40,24,5,2024, "Online", TypUdalosti.Udalost), {}, {})
        Event(Udalost(2, "Obhajoba AaUS1",0, 8,31,5,2024,30, 9,31,5,2024, "RB054", TypUdalosti.Udalost), {}, {})
        Event(Udalost(3, "Zapocet PaS",0,9, 24, 5, 2024,11,40,24,5,2024,  "RA323", TypUdalosti.Udalost), {}, {})
        Event(Udalost(4, "Skuska INF2", 0,9, 24, 5, 2024,11,40,24,5,2024,"Online", TypUdalosti.Udalost), {}, {})
        Event(Udalost(5, "Obhajoba AaUS1",  0,9, 24, 5, 2024,11,40,24,5,2024, "RB054", TypUdalosti.Udalost), {}, {})
        Event(Udalost(6, "Zapocet PaS",  12,11,7,5,2024, 55,11,11,7,2024, "RA323", TypUdalosti.Udalost), {}, {})
    }
}