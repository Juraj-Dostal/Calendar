package sk.duri.calendar.ui.monthCalendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import sk.duri.calendar.R
import sk.duri.calendar.ui.AppViewModelProvider
import sk.duri.calendar.ui.dayCalendar.Event
import sk.duri.calendar.ui.navigation.NavigationDestination

object MonthCalendarDestination : NavigationDestination {
    override val route = "monthCalendar"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthCalendarScreen(
    navigateToDayCalendar: () -> Unit,
    navigateToEventEntry: () -> Unit,
    navigateToNameDayEdit: () -> Unit,
    viewModel: MonthCalendarViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val daysUiState = viewModel.daysUiState.collectAsState()
    val days = daysUiState.value.days

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(R.string.app_name))
                },
                navigationIcon = {
                    IconButton(onClick = navigateToNameDayEdit ) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                },
                actions = {
                    IconButton(onClick = navigateToDayCalendar ) {
                        Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Change screen to DayCalendar")
                    }
                }
            )
        },
        floatingActionButton =  {
            FloatingActionButton(
                onClick = navigateToEventEntry,
            ) { Icon(Icons.Filled.Add, contentDescription = "Add") }
        }
    ){  innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
        ) {
            MonthPicker(viewModel)
            Calendar(
                days,
                viewModel,
                modifier = Modifier
                    .padding(3.dp)
            )
            DayEvents(
                viewModel,
                modifier = Modifier
                    .padding(top = 5.dp)
            )
        }
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
    days: List<Day>,
    viewModel: MonthCalendarViewModel,
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
                        days[(i - 1) * 7 + j - 1],
                        viewModel,
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
            HorizontalDivider(
                    color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
fun DayItem(
    day: Day,
    viewModel: MonthCalendarViewModel,
    modifier: Modifier = Modifier
) {
    val isActualMonth = viewModel.calendarUiState.collectAsState().value.month == day.month
    val isSelected = viewModel.selectedDay.collectAsState().value == day
    val backgroundColor = if (isSelected && isActualMonth) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background

    val textColor = if (isSelected && isActualMonth)
        MaterialTheme.colorScheme.onPrimary //else MaterialTheme.colorScheme.onBackground
    else if (isActualMonth) MaterialTheme.colorScheme.onBackground
    else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)

    val isActualDay = viewModel.actualDay.collectAsState().value == day

    val widthBorder = if (isActualDay) 2.dp else 0.dp
    val borderColor = if (isActualDay) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background

    Text(
        text = day.day.toString(),
        fontSize = 20.sp,
        style = TextStyle(color = textColor),
        maxLines = 1,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .size(48.dp)
            .clip(RectangleShape)
            .clip(MaterialTheme.shapes.large)
            .border(
                width = widthBorder,
                color = borderColor,
                shape = MaterialTheme.shapes.large
            )
            .background(backgroundColor)
            .padding(10.dp)
            .clickable(onClick = {
                viewModel.selectedDay.value = day
                viewModel.getEventsInDay(day)
            })

    )
}

@Composable
fun DayEvents(
    viewModel: MonthCalendarViewModel,
    modifier: Modifier = Modifier
) {
    val eventsDayUiState = viewModel.eventsDayUiState.collectAsState()
    val selectedDay = viewModel.selectedDay.collectAsState().value

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${selectedDay.day}.${selectedDay.month + 1}.${selectedDay.year}",
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            )
        }

        if (selectedDay.nameDay.isNotBlank()) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row {
                    Image(
                        painter = painterResource(R.drawable.celebration_24dp_fill0_wght400_grad0_opsz24),
                        contentDescription = "Celebration",
                        colorFilter = ColorFilter.tint(Color.Black),
                        modifier = Modifier
                            .size(50.dp)// Change the color to black
                    )
                    Text(
                        text = selectedDay.nameDay,
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(align = Alignment.Center)
                            .align(Alignment.CenterVertically),
                    )

                }
            }
        }

        if (eventsDayUiState.value.udalostiDni.isEmpty()) {
            Text(
                text = stringResource(R.string.NoEventToday),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                style = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                modifier = modifier
                    .padding(20.dp)
            )
        }
        else {
            eventsDayUiState.value.udalostiDni.forEach {
                Event(
                    event = it,
                    onDelete = { viewModel.deleteUdalost(it) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MonthCalendarScreenPreview() {
    MonthCalendarScreen(
        navigateToDayCalendar = {},
        navigateToEventEntry = {},
        navigateToNameDayEdit = {}
    )
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
