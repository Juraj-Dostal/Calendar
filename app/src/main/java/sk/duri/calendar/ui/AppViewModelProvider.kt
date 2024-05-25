package sk.duri.calendar.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import sk.duri.calendar.ui.CreateEvent.CreateEventViewModel
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

        // Initializer for ItemDetailsViewModel
        initializer {
            CreateEventViewModel(
                this.createSavedStateHandle(),
                udalostiApplication().container.udalostiRepository
            )
        }

    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.udalostiApplication(): UdalostiApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as UdalostiApplication)
