package sk.duri.calendar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import sk.duri.calendar.ui.dayCalendar.DayCalendarDestination
import sk.duri.calendar.ui.dayCalendar.DayCalendarScreen
import sk.duri.calendar.ui.monthCalendar.MonthCalendarDestination
import sk.duri.calendar.ui.monthCalendar.MonthCalendarScreen


@Composable
fun CalendarNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MonthCalendarDestination.route,
        modifier = modifier
    ) {
        composable(route=MonthCalendarDestination.route) {
            MonthCalendarScreen(modifier = modifier)
        }
        composable(route= DayCalendarDestination.route) {
            DayCalendarScreen()
        }
    }

}