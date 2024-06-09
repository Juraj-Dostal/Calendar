package sk.duri.calendar.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import sk.duri.calendar.UdalostiApplication
import sk.duri.calendar.ui.eventEntry.EventEntryViewModel
import sk.duri.calendar.ui.dayCalendar.DayCalendarViewModel
import sk.duri.calendar.ui.eventEntry.EventEditViewModel
import sk.duri.calendar.ui.monthCalendar.MonthCalendarViewModel
import sk.duri.calendar.ui.nameDayEdit.NameDayEditViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            MonthCalendarViewModel(udalostiApplication() ,udalostiApplication().container.udalostiRepository)
        }

        initializer {
            DayCalendarViewModel(udalostiApplication().container.udalostiRepository)
        }

        initializer {
            EventEntryViewModel(udalostiApplication().container.udalostiRepository)
        }

        initializer {
            NameDayEditViewModel(udalostiApplication())
        }
        initializer {
            EventEditViewModel(
                this.createSavedStateHandle(),
                udalostiApplication().container.udalostiRepository
            )
        }

    }
}

fun CreationExtras.udalostiApplication(): UdalostiApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as UdalostiApplication)
