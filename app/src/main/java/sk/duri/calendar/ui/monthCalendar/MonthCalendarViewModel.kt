package sk.duri.calendar.ui.monthCalendar

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import sk.duri.calendar.data.Udalost
import sk.duri.calendar.data.UdalostiRepository


class MonthCalendarViewModel(application: Application, val udalostiRepository: UdalostiRepository ) : ViewModel(){
    private val assetManager = application.assets
    private val inputStream = assetManager.open("slovak-name-day.json")
    val nameDayJson = JSONObject(inputStream.bufferedReader().use { it.readText() })



    private val calendar = java.util.Calendar.getInstance()

    private val _calendarUiState = MutableStateFlow(CalendarUiState())
    val calendarUiState: StateFlow<CalendarUiState> = _calendarUiState.asStateFlow()

    private val _daysUiState = MutableStateFlow(DaysUiState())
    val daysUiState: StateFlow<DaysUiState> = _daysUiState.asStateFlow()

    var selectedDay = MutableStateFlow(
        Day(this.calendar.get(java.util.Calendar.DAY_OF_MONTH),
            this.calendar.get(java.util.Calendar.MONTH),
            this.calendar.get(java.util.Calendar.YEAR),
            nameDayJson
                .getJSONObject(this.calendar.get(java.util.Calendar.MONTH).toString())
                .getString(this.calendar.get(java.util.Calendar.DAY_OF_MONTH).toString()) ?: ""
        )
    )
    val actualDay = MutableStateFlow(
        Day(this.calendar.get(java.util.Calendar.DAY_OF_MONTH),
            this.calendar.get(java.util.Calendar.MONTH),
            this.calendar.get(java.util.Calendar.YEAR),
            nameDayJson
                .getJSONObject(this.calendar.get(java.util.Calendar.MONTH).toString())
                .getString(this.calendar.get(java.util.Calendar.DAY_OF_MONTH).toString()) ?: ""
        )
    )

    var eventsDayUiState: MutableStateFlow<EventsDayUiState> = MutableStateFlow(EventsDayUiState())


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
        val monthJson = nameDayJson.getJSONObject(month.toString())
        val monthJsonNext = if (month == 11) nameDayJson.getJSONObject("0") else nameDayJson.getJSONObject((month + 1).toString())
        val monthJsonPrev = if (month == 0) nameDayJson.getJSONObject("11") else  nameDayJson.getJSONObject((month - 1).toString())
        val days = mutableListOf<Day>()
        var pocitadlo = 1

        this.calendar.set(year, month, 1)
        val firstDayInWeek = this.calendar.get(java.util.Calendar.DAY_OF_WEEK)
        val lastDayOfMonth = this.calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)

        this.calendar.set(year, month - 1, 1)
        val lastDayOfPrevMonth = this.calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)

        for (i in 1..firstDayInWeek - 2) {
            val hodnota =lastDayOfPrevMonth - (firstDayInWeek -2) + i
            if (month == 0)
                days.add(Day(hodnota, 11, year - 1, monthJsonPrev.getString(hodnota.toString()) ?: "" ))
            else
                days.add(Day(hodnota, month - 1, year, monthJsonPrev.getString(hodnota.toString()) ?: "" ))

            pocitadlo++
        }

        days.addAll((1..lastDayOfMonth).map{ Day(it, month, year, monthJson.getString(it.toString()) ?: "" ) })
        pocitadlo += 28

        if (month == 11)
            days.addAll((1..43 - pocitadlo).map{ Day(it, 0, year + 1, monthJsonNext.getString(it.toString()) ?: "" ) })
        else
            days.addAll((1..43 - pocitadlo).map{ Day(it, month + 1, year, monthJsonNext.getString(it.toString()) ?: "" ) })

        _daysUiState.value = DaysUiState(days)
    }

    fun getEventsInDay(day: Day = selectedDay.value) {

        viewModelScope.launch {
            udalostiRepository.getUdalostiVDni(day.day, day.month + 1, day.year).collect{
                eventsDayUiState.value = EventsDayUiState(it)
            }
        }

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
    val name_Day: String,
)
