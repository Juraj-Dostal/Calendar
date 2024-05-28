package sk.duri.calendar.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import sk.duri.calendar.UdalostiApplication
import sk.duri.calendar.ui.eventEntry.EventEntryViewModel
import sk.duri.calendar.ui.dayCalendar.DayCalendarViewModel
import sk.duri.calendar.ui.monthCalendar.MonthCalendarViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for MonthCalendarViewModel
        initializer {
            MonthCalendarViewModel()
        }
        // Initializer for DayCalendarViewModel
        initializer {
            DayCalendarViewModel()
        }

        // Initializer for EventEntryViewModel
        initializer {
            EventEntryViewModel(udalostiApplication().container.udalostiRepository)
        }


    }
}

fun CreationExtras.udalostiApplication(): UdalostiApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as UdalostiApplication)
