package sk.duri.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import sk.duri.calendar.ui.eventEntry.EventEntryDestination
import sk.duri.calendar.ui.dayCalendar.DayCalendarDestination
import sk.duri.calendar.ui.monthCalendar.MonthCalendarDestination
import sk.duri.calendar.ui.navigation.CalendarNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarApp(navController: NavHostController = rememberNavController()) {


    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Calendar")
                },
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(MonthCalendarDestination.route) }) {
                        Icon(Icons.Filled.DateRange, contentDescription = "Change screen to MonthCalendar")
                    }
                    IconButton(onClick = { navController.navigate(DayCalendarDestination.route) }) {
                        Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Change screen to DayCalendar")
                    }
                }
            )
        },
        floatingActionButton =  {
            FloatingActionButton(
                onClick = { navController.navigate(EventEntryDestination.route) },
            ) { Icon(Icons.Filled.Add, contentDescription = "Add") }
        }
    ){
        innerPadding ->
        Column(
        modifier = Modifier
            .padding(innerPadding)
    ) {
            CalendarNavHost(navController = navController)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalendarAppPreview() {
    CalendarApp()
}