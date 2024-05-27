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
        Event(Udalost(1, "Skuska INF2", 0,9, 24, 5, 2024,11,40,24,5,2024, "Online", TypUdalosti.Udalost))
        Event(Udalost(2, "Obhajoba AaUS1",0, 8,31,5,2024,30, 9,31,5,2024, "RB054", TypUdalosti.Udalost))
        Event(Udalost(3, "Zapocet PaS",0,9, 24, 5, 2024,11,40,24,5,2024,  "RA323", TypUdalosti.Udalost))
        Event(Udalost(4, "Skuska INF2", 0,9, 24, 5, 2024,11,40,24,5,2024,"Online", TypUdalosti.Udalost))
        Event(Udalost(5, "Obhajoba AaUS1",  0,9, 24, 5, 2024,11,40,24,5,2024, "RB054", TypUdalosti.Udalost))
        Event(Udalost(6, "Zapocet PaS",  12,11,7,5,2024, 55,11,11,7,2024, "RA323", TypUdalosti.Udalost))

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
            text = event.nazov,
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
                    text = "${event.odHodina}:${event.odMinuta}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "${event.doHodina}:${event.doMinuta}",
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