package sk.duri.calendar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import sk.duri.calendar.ui.CreateEvent.CreateEventDestination
import sk.duri.calendar.ui.CreateEvent.CreateEventScreen
import sk.duri.calendar.ui.dayCalendar.DayCalendarDestination
import sk.duri.calendar.ui.dayCalendar.DayCalendarScreen
import sk.duri.calendar.ui.eventEntry.EventEntryDestination
import sk.duri.calendar.ui.eventEntry.EventEntryScreen
import sk.duri.calendar.ui.monthCalendar.MonthCalendarDestination
import sk.duri.calendar.ui.monthCalendar.MonthCalendarScreen


@Composable
fun CalendarNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MonthCalendarDestination.route,
        modifier = modifier
    ) {
        composable(route=MonthCalendarDestination.route) {
            MonthCalendarScreen(
                navigateToDayCalendar = { navController.navigate(DayCalendarDestination.route) },
                navigateToEventEntry = { navController.navigate(EventEntryDestination.route) },
                modifier = modifier
            )
        }
        composable(route= DayCalendarDestination.route) {
            DayCalendarScreen(
                navigateToEventEntry = { navController.navigate(EventEntryDestination.route) },
                navigateToMonthCalendar = { navController.navigate(MonthCalendarDestination.route) }
            )
        }
        composable(route = CreateEventDestination.route){
            CreateEventScreen(navigateBack =  { navController.popBackStack()  })
        }
        composable(route = EventEntryDestination.route){
            EventEntryScreen(
                navigateBack =  { navController.popBackStack()  }
            )
        }
    }

}
