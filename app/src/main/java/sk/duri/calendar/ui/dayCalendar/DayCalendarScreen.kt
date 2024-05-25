package sk.duri.calendar.ui.dayCalendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.duri.calendar.R
import sk.duri.calendar.data.TypUdalosti
import sk.duri.calendar.data.Udalost
import sk.duri.calendar.ui.navigation.NavigationDestination
import java.util.Calendar

object DayCalendarDestination : NavigationDestination {
    override val route = "dayCalendar"
}

@Composable
fun DayCalendarScreen() {
    Column (Modifier.verticalScroll(rememberScrollState())) {
        Event(Udalost(1, "Skuska INF2", Calendar.getInstance().apply{set(2024,5,24,9,0)}, Calendar.getInstance().apply{set(2024,5,24,11,40)}, "Online", TypUdalosti.Udalost))
        Event(Udalost(2, "Obhajoba AaUS1", Calendar.getInstance().apply{set(2024,5,31,8,0)}, Calendar.getInstance().apply{set(2024,5,31,8,15)},  "RB054", TypUdalosti.Udalost))
        Event(Udalost(3, "Zapocet PaS", Calendar.getInstance().apply{set(2024,5,5,9,14)}, Calendar.getInstance().apply{set(2024,5,5,9,44)},  "RA323", TypUdalosti.Udalost))
        Event(Udalost(4, "Skuska INF2", Calendar.getInstance().apply{set(2024,6,24,14,30)}, Calendar.getInstance().apply{set(2024,6,25,14,30)},  "Online", TypUdalosti.Udalost))
        Event(Udalost(5, "Obhajoba AaUS1",  Calendar.getInstance().apply{set(2024,5,17,10,0)},  Calendar.getInstance().apply{set(2024,5,18,10,0)}, "RB054", TypUdalosti.Udalost))
        Event(Udalost(6, "Zapocet PaS",  Calendar.getInstance().apply{set(2024,5,7,11,12)}, Calendar.getInstance().apply{set(2024,5,7,11,55)}, "RA323", TypUdalosti.Udalost))

    }
}



@Composable
fun Event(
    event: Udalost,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.padding(5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
        ) {
            Text(
            text = event.name,
            style = MaterialTheme.typography.titleLarge
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.from),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = stringResource(R.string.to),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${event.dateFrom.get(Calendar.DAY_OF_MONTH)}.${event.dateFrom.get(Calendar.MONTH)}.${event.dateFrom.get(Calendar.YEAR)}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "${event.dateFrom.get(Calendar.DAY_OF_MONTH)}.${event.dateFrom.get(Calendar.MONTH)}.${event.dateFrom.get(Calendar.YEAR)}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${event.dateFrom.get(Calendar.HOUR).toString()}:${event.dateFrom.get(Calendar.MINUTE)}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "${event.dateTo.get(Calendar.HOUR)}:${event.dateTo.get(Calendar.MINUTE)}",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Text(
                text = event.note,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@Preview( showBackground = true)
@Composable
fun DayCalendarScreenPreview() {
    DayCalendarScreen()
}