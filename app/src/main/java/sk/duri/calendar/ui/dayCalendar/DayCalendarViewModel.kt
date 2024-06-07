package sk.duri.calendar.ui.dayCalendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sk.duri.calendar.data.Udalost
import sk.duri.calendar.data.UdalostiRepository

class DayCalendarViewModel(val udalostiRepository: UdalostiRepository) : ViewModel() {

    val dayCalendarUiState: StateFlow<DayCalendarUiState> =
        udalostiRepository.getUdalosti().map{DayCalendarUiState(it)}
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DayCalendarUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun deleteUdalost(udalost: Udalost) {
        viewModelScope.launch {
            udalostiRepository.deleteUdalost(udalost)
        }
    }
}

data class DayCalendarUiState(
    val udalostZoznam: List<Udalost> = listOf()
)