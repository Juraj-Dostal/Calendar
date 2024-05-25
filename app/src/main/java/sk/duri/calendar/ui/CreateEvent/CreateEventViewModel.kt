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

    fun updateUiState(udalostDetails: UdalostDetails) {
        udalostUiState = UdalostUiState(udalostDetails = udalostDetails, isEntryValid = validateInput(udalostDetails))
    }

    private fun validateInput(uiState: UdalostDetails = udalostUiState.udalostDetails): Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }

    suspend fun saveEvent() {
        udalostiRepository.insertUdalost(udalostUiState.udalostDetails.toUdalost())
    }


}

data class UdalostUiState(
    val udalostDetails: UdalostDetails = UdalostDetails(),
    val isEntryValid: Boolean = false
)

data class UdalostDetails(
    val id: Int = 0,
    val name: String = "",
    val dateFrom: Calendar = Calendar.getInstance(),
    val dateTo: Calendar = Calendar.getInstance(),
    val note: String = "",
    val typ: TypUdalosti = TypUdalosti.Udalost
)

fun UdalostDetails.toUdalost(): Udalost = Udalost(
    id = id,
    name = name,
    dateFrom = dateFrom,
    dateTo = dateTo,
    note = note,
    typ = typ
)

fun Udalost.toUdalostUiState(isEntryValid: Boolean = false): UdalostUiState = UdalostUiState(
    udalostDetails = this.toUdalostDetails(),
    isEntryValid = isEntryValid
)

fun Udalost.toUdalostDetails(): UdalostDetails =  UdalostDetails(
    id = id,
    name = name,
    dateFrom = dateFrom,
    dateTo = dateTo,
    note = note,
    typ = typ
)