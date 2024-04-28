package sk.duri.calendar.ui.dayCalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sk.duri.calendar.R

@Composable
fun DayCalendarScreen() {
    Column {
        WeekBar()
        DayScreen()
    }
}

@Composable
fun WeekBar(
    modifier: Modifier = Modifier
) {
    val nameOfDayOfWeek = LocalContext.current.resources.getStringArray(R.array.days_in_week)
    val numberOfWeek = intArrayOf(29, 30, 1, 2, 3, 4, 5)
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { /**/ }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Previous month")
        }

        Box(modifier = Modifier.weight(1f)) {
            Column {
                Row(
                    modifier.background(MaterialTheme.colorScheme.primary),
                ) {
                    nameOfDayOfWeek.forEach {
                        Text(
                            text = it,
                            modifier = Modifier
                                .weight(1f),
                            textAlign = TextAlign.Center,
                            fontSize = 15.sp,
                            style = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
                        )
                    }
                }

                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    for (j in 1..7) {
                        Day(
                            numberOfDay = numberOfWeek[j - 1],
                            modifier = Modifier
                                .weight(1f)
                        )
                    }
                }
            }
        }

        IconButton(onClick = { /**/ }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Next month")
        }
    }
}

@Composable
fun Day(
    numberOfDay: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = numberOfDay.toString(),
        modifier = modifier,
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        style = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
    )
}

@Composable
fun DayScreen(
    modifier: Modifier = Modifier
) {
    val items = (0..46).toList()
    var hour = 0

    LazyColumn(modifier = modifier.fillMaxSize()) {
        items((0..48).toList()) { i ->
            if (i % 2 == 0) {
                HourSlot(hour = i / 2, minute = 0)
            } else {
                HourSlot(hour = i / 2, minute = 3)
            }
        }
    }
}

@Composable
fun HourSlot(
    hour: Int,
    minute: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row() {
            Text(
                text = "$hour:$minute" + "0",
                style = TextStyle(fontSize = 20.sp, color = MaterialTheme.colorScheme.onBackground),
                modifier = Modifier
                    .padding(start = 16.dp)
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(12.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DayCalendarScreenPreview() {
    DayCalendarScreen()
}