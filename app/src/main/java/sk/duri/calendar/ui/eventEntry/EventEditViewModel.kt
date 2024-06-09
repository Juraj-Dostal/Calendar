package sk.duri.calendar.ui.eventEntry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import sk.duri.calendar.data.UdalostiRepository

class EventEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val udalostiRepository: UdalostiRepository
) : ViewModel(){

    var udalostUiState by mutableStateOf(UdalostUiState())
        private set


    private val eventId: Int = checkNotNull(savedStateHandle[EventEditDestination.eventIdArg])

    init {
        viewModelScope.launch {
            udalostUiState = udalostiRepository.getUdalost(eventId)
                .filterNotNull()
                .first()
                .toUdalostUiState(true)
        }
    }


    private fun validateInput(uiState: UdalostDetails = udalostUiState.udalostDetails): Boolean {
        return with(uiState) {
            nazov.isNotBlank()
                    && odMinuta.isNotBlank()
                    && odHodina.isNotBlank()
                    && odDen.isNotBlank()
                    && odMesiac.isNotBlank()
                    && odRok.isNotBlank()
                    && doMinuta.isNotBlank()
                    && doHodina.isNotBlank()
                    && doDen.isNotBlank()
                    && doMesiac.isNotBlank()
                    && doRok.isNotBlank()
        }
    }

    fun updateUiState(udalostDetails: UdalostDetails) {
        udalostUiState = UdalostUiState(udalostDetails = udalostDetails, isEntryValid = validateInput(udalostDetails))
    }

    suspend fun updateEvent() {
        if (validateInput(udalostUiState.udalostDetails)) {
            udalostiRepository.updateUdalost(udalostUiState.udalostDetails.toUdalost())
        }
    }
}