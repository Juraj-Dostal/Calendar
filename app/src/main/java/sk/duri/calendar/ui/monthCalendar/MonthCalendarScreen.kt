package sk.duri.calendar.ui.monthCalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import sk.duri.calendar.R
import java.util.Calendar


@Composable
fun MonthCalendarScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar()
        Calendar()
        DayEvents(/*udalosti = List<Udalosti>*/)
    }
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .background(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
            ) {
            IconButton(onClick = { /**/ }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Previous month")
            }
            Text(
                text = "January 2023",
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 20.sp, color = MaterialTheme.colorScheme.onPrimary),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            IconButton(
                onClick = { /**/ },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Next month")
            }
        }
    }
}

@Composable
fun Calendar(
    modifier: Modifier = Modifier
) {
    val nameOfDayOfWeek = LocalContext.current.resources.getStringArray(R.array.days_in_week)

    Column(modifier.background(MaterialTheme.colorScheme.background)) {
        Row(
            modifier.background(MaterialTheme.colorScheme.primary),
        ) {
            nameOfDayOfWeek.forEach {
                Text(
                    text = it,
                    modifier = Modifier
                        .weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    style = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
                )
            }
        }
        for (i in 1..6) {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                for (j in 1..7) {
                    DayItem(
                        numberOfDay = i*j,
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun DayItem(
    numberOfDay: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = numberOfDay.toString(),
        modifier = modifier,
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        style = TextStyle(color = MaterialTheme.colorScheme.onBackground)
    )
}

@Composable
fun DayEvents(
    //udalosti: List<Udalosti>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Events",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        }

        Text(
            text = "No events",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            style = TextStyle(color = MaterialTheme.colorScheme.onBackground)
        )

        /*if (udalosti.isEmpty()) {
            Text(
                text = "No events",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                style = TextStyle(color = MaterialTheme.colorScheme.onBackground)
            )
        }
        else{
            udalosti.forEach {
                Text(
                    text = it.name,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    style = TextStyle(color = MaterialTheme.colorScheme.onBackground)
                )
            }

        }*/


    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MonthCalendarScreenPreview() {
    MonthCalendarScreen()
}
