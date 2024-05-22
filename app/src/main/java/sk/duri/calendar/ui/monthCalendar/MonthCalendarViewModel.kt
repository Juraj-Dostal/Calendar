package sk.duri.calendar.ui.monthCalendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sk.duri.calendar.R


class MonthCalendarViewModel() : ViewModel(){
    private val calendar = java.util.Calendar.getInstance()

    private val _calendarUiState = MutableStateFlow(CalendarUiState())
    val calendarUiState: StateFlow<CalendarUiState> = _calendarUiState.asStateFlow()

    private val _daysUiState = MutableStateFlow(DaysUiState())
    val daysUiState: StateFlow<DaysUiState> = _daysUiState.asStateFlow()


    init {
        _calendarUiState.value = CalendarUiState(
            this.calendar.get(java.util.Calendar.MONTH),
            this.calendar.get(java.util.Calendar.YEAR)
        )
        setInitialDays()
    }

    public fun setDate(addMonth: Int) {
        var year: Int = this._calendarUiState.value.year
        var month: Int = this._calendarUiState.value.month

        month += addMonth
        if (month > 11) {
            month = 0
            year++
        } else if (month < 0) {
            month = 11
            year--
        }

        _calendarUiState.value = CalendarUiState(month, year)

        setInitialDays()
    }

    private fun setInitialDays() {
        var year: Int = this._calendarUiState.value.year
        var month: Int = this._calendarUiState.value.month
        val days = mutableListOf<Int>()
        var pocitadlo = 1

        this.calendar.set(year, month, 1)
        val firstDayInWeek = this.calendar.get(java.util.Calendar.DAY_OF_WEEK)
        val lastDayOfMonth = this.calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)

        this.calendar.set(year, month - 1, 1)
        val lastDayOfPrevMonth = this.calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)

        for (i in 1..firstDayInWeek - 2) {
            days.add(lastDayOfPrevMonth - (firstDayInWeek -2) + i)
            pocitadlo++
        }

        days.addAll(1..28)
        pocitadlo += 28

        for (i in 29..lastDayOfMonth) {
            days.add(i)
            pocitadlo++
        }
        for (i in 1..43 - pocitadlo) {
            days.add(i)
        }

        _daysUiState.value = DaysUiState(days)
    }

    /*private fun getMonthName(): String {
        return when (11) {
            0 -> "January"
            1 -> "February"
            2 -> "March"
            3 -> "April"
            4 -> "May"
            5 -> "June"
            6 -> "July"
            7 -> "August"
            8 -> "September"
            9 -> "October"
            10 -> "November"
            11 -> "December"
            else -> ""
        }
    }*/
}

data class CalendarUiState(
    var month: Int = -1,
    var year: Int = -1
)

data class DaysUiState(
    val days: List<Int> = listOf()
)
