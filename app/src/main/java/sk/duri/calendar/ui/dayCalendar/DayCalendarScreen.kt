package sk.duri.calendar.ui.dayCalendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.sp
import sk.duri.calendar.R
import sk.duri.calendar.data.TypUdalosti
import sk.duri.calendar.data.Udalost
import sk.duri.calendar.ui.navigation.NavigationDestination
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date

object DayCalendarDestination : NavigationDestination {
    override val route = "dayCalendar"
}

@Composable
fun DayCalendarScreen() {
    Column (Modifier.verticalScroll(rememberScrollState())) {
        Event(Udalost(1, "Skuska INF2", Date(2024,5,24), Date(2024,5,24), LocalTime.of(9,0), LocalTime.of(11,40), "Online", TypUdalosti.Udalost))
        Event(Udalost(2, "Obhajoba AaUS1", Date(2024,5,27), Date(2024,5,27), LocalTime.of(9,0), LocalTime.of(11,40), "RB054", TypUdalosti.Udalost))
        Event(Udalost(3, "Zapocet PaS", Date(2024,5,29), Date(2024,5,30), LocalTime.of(16,0), LocalTime.of(16,40), "RA323", TypUdalosti.Udalost))
        Event(Udalost(4, "Skuska INF2", Date(2024,5,24), Date(2024,5,24), LocalTime.of(9,0), LocalTime.of(11,40), "Online", TypUdalosti.Udalost))
        Event(Udalost(5, "Obhajoba AaUS1", Date(2024,5,27), Date(2024,5,27), LocalTime.of(9,0), LocalTime.of(11,40), "RB054", TypUdalosti.Udalost))
        Event(Udalost(6, "Zapocet PaS", Date(2024,5,29), Date(2024,5,30), LocalTime.of(16,0), LocalTime.of(16,40), "RA323", TypUdalosti.Udalost))

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
                    text = "${event.dateFrom.date}.${event.dateFrom.month}.${event.dateFrom.year}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "${event.dateTo.day}.${event.dateTo.month}.${event.dateTo.date}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = event.timeFrom.format(DateTimeFormatter.ofPattern("HH:mm")),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = event.timeTo.format(DateTimeFormatter.ofPattern("HH:mm")),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Text(
                text = event.poznamka,
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