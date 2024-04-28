package sk.duri.calendar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController


@Composable
fun CalendarNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    /*NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route=CalendarNavDestinations.Home.route) {
            CalendarScreen(
            )
        }
    }*/
}