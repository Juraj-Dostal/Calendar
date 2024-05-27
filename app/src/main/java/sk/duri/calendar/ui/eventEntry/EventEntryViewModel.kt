package sk.duri.calendar.ui.eventEntry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import sk.duri.calendar.data.CalendarDatabase
import sk.duri.calendar.data.OfflineUdalostiRepository
import sk.duri.calendar.data.TypUdalosti
import sk.duri.calendar.data.Udalost
import sk.duri.calendar.data.UdalostiRepository

class EventEntryViewModel(private val udalostiRepository: UdalostiRepository )  : ViewModel() {

    var udalostUiState by mutableStateOf(UdalostUiState())
        private set

    fun updateUiState(udalostDetails: UdalostDetails) {
        udalostUiState = UdalostUiState(udalostDetails = udalostDetails, isEntryValid = validateInput(udalostDetails))
    }

    private fun validateInput(uiState: UdalostDetails = udalostUiState.udalostDetails): Boolean {
        return with(uiState) {
            nazov.isNotBlank() && odMinuta in 0..59 && odHodina in 0..23 && odDen in 1..31 && odMesiac in 1..12 && odRok > 2020 &&
                    doMinuta in 0..59 && doHodina in 0..23 && doDen in 1..31 && doMesiac in 1..12 && doRok > 2020
        }
    }

    suspend fun saveUdalost() {
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
    val nazov: String = "",
    val odMinuta: Int = 0,
    val odHodina: Int = 0,
    val odDen: Int = 0,
    val odMesiac: Int = 0,
    val odRok: Int = 0,
    val doMinuta: Int = 0,
    val doHodina: Int = 0,
    val doDen: Int = 0,
    val doMesiac: Int = 0,
    val doRok: Int = 0,
    val poznamka: String = "",
    val typ: TypUdalosti = TypUdalosti.Udalost
)

fun UdalostDetails.toUdalost(): Udalost = Udalost(
    id = id,
    nazov = nazov,
    odMinuta = odMinuta,
    odHodina = odHodina,
    odDen = odDen,
    odMesiac = odMesiac,
    odRok = odRok,
    doMinuta = doMinuta,
    doHodina = doHodina,
    doDen = doDen,
    doMesiac = doMesiac,
    doRok = doRok,
    poznamka = poznamka,
    typ = typ
)

fun Udalost.toUdalostUiState(isEntryValid: Boolean = false): UdalostUiState = UdalostUiState(
    udalostDetails = this.toUdalostDetails(),
    isEntryValid = isEntryValid
)

fun Udalost.toUdalostDetails(): UdalostDetails =  UdalostDetails(
    id, nazov, odMinuta, odHodina, odDen, odMesiac, odRok, doMinuta, doHodina, doDen, doMesiac, doRok, poznamka
)
