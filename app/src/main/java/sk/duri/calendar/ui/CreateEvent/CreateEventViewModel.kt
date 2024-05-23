package sk.duri.calendar.ui.CreateEvent

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import sk.duri.calendar.data.TypUdalosti
import sk.duri.calendar.data.Udalost
import sk.duri.calendar.data.UdalostiRepository
import java.time.LocalTime
import java.util.Date

class CreateEventViewModel(private val udalostiRepository: UdalostiRepository) {

    var udalostUiState by mutableStateOf(UdalostUiState())
        private set

    fun updateUiState(udalostDetails: UdalostDetails) {
        udalostUiState = UdalostUiState(udalostDetails = udalostDetails, isEntryValid = validateInput(udalostDetails))
    }

    private fun validateInput(uiState: UdalostDetails = udalostUiState.udalostDetails): Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }

    private suspend fun saveUdalost() {
        if (validateInput()) {
            udalostiRepository.insertUdalost(udalostUiState.udalostDetails.toUdalost())
        }
    }


}

data class UdalostUiState(
    val udalostDetails: UdalostDetails = UdalostDetails(),
    val isEntryValid: Boolean = false
)

data class UdalostDetails(
    val id: Int = 0,
    val name: String = "",
    val dateFrom: Date = Date(),
    val dateTo: Date = Date(),
    val timeFrom: LocalTime = LocalTime.of(12, 0),
    val timeTo: LocalTime = LocalTime.of(13, 0),
    val poznamka: String = "",
    val typ: TypUdalosti = TypUdalosti.Udalost
) {
    fun toUdalost(): Udalost = Udalost(
        id = id,
        name = name,
        dateFrom = dateFrom,
        dateTo = dateTo,
        timeFrom = timeFrom,
        timeTo = timeTo,
        poznamka = poznamka,
        typ = typ
    )
}