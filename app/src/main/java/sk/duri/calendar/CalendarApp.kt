package sk.duri.calendar


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import sk.duri.calendar.ui.navigation.CalendarNavHost


@Composable
fun CalendarApp(navController: NavHostController = rememberNavController()) {

    CalendarNavHost(navController = navController)

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalendarAppPreview() {
    CalendarApp()
}