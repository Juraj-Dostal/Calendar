package sk.duri.calendar.ui.dayCalendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import java.time.LocalTime
import java.util.Date

object DayCalendarDestination : NavigationDestination {
    override val route = "dayCalendar"
}

@Composable
fun DayCalendarScreen() {
    Column {
        Event(Udalost(1, "name", Date(), Date(), LocalTime.now(), LocalTime.now(), "description", TypUdalosti.Udalost))
        Event(Udalost(1, "name", Date(), Date(), LocalTime.now(), LocalTime.now(), "description", TypUdalosti.Udalost))
        Event(Udalost(1, "name", Date(), Date(), LocalTime.now(), LocalTime.now(), "description", TypUdalosti.Udalost))

    }
}



@Composable
fun Event(
    event: Udalost,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier,
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
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = stringResource(R.string.to),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${event.dateFrom.day}.${event.dateFrom.month}.${event.dateFrom.date}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "${event.dateTo.day}.${event.dateTo.month}.${event.dateTo.date}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${event.timeFrom.hour}:${event.timeFrom.minute}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "${event.timeTo.hour}:${event.timeTo.minute}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}


@Preview( showBackground = true)
@Composable
fun DayCalendarScreenPreview() {
    DayCalendarScreen()
}