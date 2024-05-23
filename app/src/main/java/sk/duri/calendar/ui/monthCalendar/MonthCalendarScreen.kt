package sk.duri.calendar.ui.monthCalendar

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import sk.duri.calendar.R
import sk.duri.calendar.ui.navigation.NavigationDestination

object MonthCalendarDestination : NavigationDestination {
    override val route = "monthCalendar"
}

@Composable
fun MonthCalendarScreen(

    viewModel: MonthCalendarViewModel = MonthCalendarViewModel(),
    modifier: Modifier = Modifier
) {
    val daysUiState = viewModel.daysUiState.collectAsState()
    val days = daysUiState.value.days

    Column(modifier = Modifier.fillMaxSize()) {
        MonthPicker(viewModel)
        Calendar(days)
        DayEvents(/*udalosti = List<Udalosti>*/)
    }
}

@Composable
fun MonthPicker(
    viewModel: MonthCalendarViewModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .background(MaterialTheme.colorScheme.primary)
    ) {
        val calendarUiState = viewModel.calendarUiState.collectAsState()
        val year = calendarUiState.value.year
        val month = getMonthName(month = calendarUiState.value.month)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
            ) {
            IconButton(onClick = { viewModel.setDate(addMonth = -1) } ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Previous month")
            }
            Text(
                text = "$month $year",
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 20.sp, color = MaterialTheme.colorScheme.onPrimary),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            IconButton( onClick = { viewModel.setDate(addMonth = 1) } ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Next month")
            }
        }
    }
}



@Composable
fun Calendar(
    days: List<Int>,
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
                        numberOfDay = days[(i - 1) * 7 + j - 1],
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
        fontSize = 20.sp,
        style = TextStyle(color = MaterialTheme.colorScheme.onBackground),
        maxLines = 1,
        textAlign = TextAlign.Center,
        modifier = modifier.padding(10.dp)
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

@Preview(showBackground = true)
@Composable
fun MonthCalendarScreenPreview() {
    MonthCalendarScreen(modifier = Modifier)
}

@Composable
private fun getMonthName(month: Int): String {
    val context = LocalContext.current
    return when (month) {
        0 -> context.getString(R.string.january)
        1 -> context.getString(R.string.february)
        2 -> context.getString(R.string.march)
        3 -> context.getString(R.string.april)
        4 -> context.getString(R.string.may)
        5 -> context.getString(R.string.june)
        6 -> context.getString(R.string.july)
        7 -> context.getString(R.string.august)
        8 -> context.getString(R.string.september)
        9 -> context.getString(R.string.october)
        10 -> context.getString(R.string.november)
        11 -> context.getString(R.string.december)
        else -> ""
    }
}
