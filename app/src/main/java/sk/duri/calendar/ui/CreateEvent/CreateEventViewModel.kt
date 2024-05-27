package sk.duri.calendar.ui.CreateEvent

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import sk.duri.calendar.data.TypUdalosti
import sk.duri.calendar.data.Udalost
import sk.duri.calendar.data.UdalostiRepository
import java.util.Calendar

class CreateEventViewModel(
    savedStateHandle: SavedStateHandle,
    val udalostiRepository: UdalostiRepository
) : ViewModel(){
/*
    init {
        viewModelScope.launch {
            udalostUiState = udalostiRepository.getUdalost(udalostId)
                .filterNotNull()
                .first()
                .toUdalostUiState(true)
        }
    }
    var udalostUiState by mutableStateOf(UdalostUiState())
        private set

    private val udalostId: Int = checkNotNull(savedStateHandle[CreateEventDestination.udalostIdArg])

    private fun validateInput(uiState: UdalostDetails = udalostUiState.udalostDetails): Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }

    fun updateUiState(udalostDetails: UdalostDetails) {
        udalostUiState = UdalostUiState(udalostDetails = udalostDetails, isEntryValid = validateInput(udalostDetails))
    }

    suspend fun updateEvent() {
        if (validateInput(udalostUiState.udalostDetails)) {
            udalostiRepository.updateUdalost(udalostUiState.udalostDetails.toUdalost())
        }
    }*/

}

