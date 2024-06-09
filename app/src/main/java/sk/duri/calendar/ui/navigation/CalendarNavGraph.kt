package sk.duri.calendar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import sk.duri.calendar.ui.dayCalendar.DayCalendarDestination
import sk.duri.calendar.ui.dayCalendar.DayCalendarScreen
import sk.duri.calendar.ui.eventEntry.EventEditDestination
import sk.duri.calendar.ui.eventEntry.EventEditScreen
import sk.duri.calendar.ui.eventEntry.EventEntryDestination
import sk.duri.calendar.ui.eventEntry.EventEntryScreen
import sk.duri.calendar.ui.monthCalendar.MonthCalendarDestination
import sk.duri.calendar.ui.monthCalendar.MonthCalendarScreen
import sk.duri.calendar.ui.nameDayEdit.NameDayEditDestination
import sk.duri.calendar.ui.nameDayEdit.NameDayEditScreen


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
                navigateToNameDayEdit = { navController.navigate(NameDayEditDestination.route) },
                navigateToEventEdit = { navController.navigate("${EventEditDestination.route}/${it}") },
                modifier = modifier
            )
        }
        composable(route= DayCalendarDestination.route) {
            DayCalendarScreen(
                navigateToEventEntry = { navController.navigate(EventEntryDestination.route) },
                navigateToMonthCalendar = { navController.navigate(MonthCalendarDestination.route) },
                navigateToNameDayEdit = { navController.navigate(NameDayEditDestination.route) },
                navigateToEventEdit = { navController.navigate("${EventEditDestination.route}/${it}") }
            )
        }

        composable(route = EventEntryDestination.route){
            EventEntryScreen(
                navigateToNameDayEdit = { navController.navigate(NameDayEditDestination.route) },
                navigateBack =  { navController.popBackStack()  }
            )
        }

        composable(route = NameDayEditDestination.route){
            NameDayEditScreen(
                navigateToDayCalendar = { navController.navigate(DayCalendarDestination.route) },
                navigateToMonthCalendar = { navController.navigate(MonthCalendarDestination.route) },
                navigateToEventEntry = { navController.navigate(EventEntryDestination.route) }
            )
        }

        composable(
            route = EventEditDestination.routeWithArgs,
            arguments = listOf(
                navArgument(EventEditDestination.eventIdArg) {
                type = NavType.IntType
            })
        ){
           EventEditScreen(
               navigateBack = { navController.popBackStack() },
               navigateToNameDayEdit = { navController.navigate(NameDayEditDestination.route) }
           )

        }


    }

}
