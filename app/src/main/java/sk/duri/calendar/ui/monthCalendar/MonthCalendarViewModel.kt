package sk.duri.calendar.ui.monthCalendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import sk.duri.calendar.data.Udalost
import sk.duri.calendar.data.UdalostiRepository


class MonthCalendarViewModel(val udalostiRepository: UdalostiRepository ) : ViewModel(){
    private val calendar = java.util.Calendar.getInstance()

    private val _calendarUiState = MutableStateFlow(CalendarUiState())
    val calendarUiState: StateFlow<CalendarUiState> = _calendarUiState.asStateFlow()

    private val _daysUiState = MutableStateFlow(DaysUiState())
    val daysUiState: StateFlow<DaysUiState> = _daysUiState.asStateFlow()

    var selectedDay = MutableStateFlow(
        Day(this.calendar.get(java.util.Calendar.DAY_OF_MONTH),
            this.calendar.get(java.util.Calendar.MONTH),
            this.calendar.get(java.util.Calendar.YEAR)))
    val actualDay = MutableStateFlow(
        Day(this.calendar.get(java.util.Calendar.DAY_OF_MONTH),
            this.calendar.get(java.util.Calendar.MONTH),
            this.calendar.get(java.util.Calendar.YEAR)))

    var eventsDayUiState: StateFlow<EventsDayUiState> = MutableStateFlow(EventsDayUiState()).asStateFlow()
        /*udalostiRepository.getUdalostiVDni(selectedDay.value.day, selectedDay.value.month + 1, selectedDay.value.year)
            .map{EventsDayUiState(it)}
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = EventsDayUiState()
            )*/

    init {
        _calendarUiState.value = CalendarUiState(
            this.calendar.get(java.util.Calendar.MONTH),
            this.calendar.get(java.util.Calendar.YEAR)
        )

        setInitialDays()
        getEventsInDay()
    }

     fun setDate(addMonth: Int) {
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
        val year: Int = this._calendarUiState.value.year
        val month: Int = this._calendarUiState.value.month
        val days = mutableListOf<Day>()
        var pocitadlo = 1

        this.calendar.set(year, month, 1)
        val firstDayInWeek = this.calendar.get(java.util.Calendar.DAY_OF_WEEK)
        val lastDayOfMonth = this.calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)

        this.calendar.set(year, month - 1, 1)
        val lastDayOfPrevMonth = this.calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)

        for (i in 1..firstDayInWeek - 2) {
            if (month == 0)
                days.add(Day(lastDayOfPrevMonth - (firstDayInWeek -2) + i, 11, year - 1))
            else
                days.add(Day(lastDayOfPrevMonth - (firstDayInWeek -2) + i, month - 1, year))

            pocitadlo++
        }

        days.addAll((1..lastDayOfMonth).map{ Day(it, month, year) })
        pocitadlo += 28

        if (month == 11)
            days.addAll((1..43 - pocitadlo).map{ Day(it, month + 1, year) })
        else
            days.addAll((1..43 - pocitadlo).map{ Day(it, month + 1, year) })

        _daysUiState.value = DaysUiState(days)
    }

    fun getEventsInDay(day: Day = selectedDay.value) {
        eventsDayUiState = udalostiRepository
            .getUdalostiVDni(day.day, day.month + 1, day.year)
            .map { EventsDayUiState(it)}
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = EventsDayUiState()
            )
    }
}


data class EventsDayUiState(
    var udalostiDni: List<Udalost> = listOf()
)

data class CalendarUiState(
    var month: Int = 0,
    var year: Int = 0
)

data class DaysUiState(
    val days: List<Day> = listOf()
)

data class Day(
    val day: Int,
    val month: Int,
    val year: Int,
)
