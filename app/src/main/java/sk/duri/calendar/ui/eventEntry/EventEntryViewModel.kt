package sk.duri.calendar.ui.eventEntry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
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
    val odMinuta: String = "",
    val odHodina: String = "",
    val odDen: String = "",
    val odMesiac: String = "",
    val odRok: String = "",
    val doMinuta: String = "",
    val doHodina: String = "",
    val doDen: String = "",
    val doMesiac: String = "",
    val doRok: String = "",
    val poznamka: String = "",
    val typ: TypUdalosti = TypUdalosti.Udalost
)

fun UdalostDetails.toUdalost(): Udalost = Udalost(
    id = id,
    nazov = nazov,
    odMinuta = odMinuta.toIntOrNull() ?: 0,
    odHodina = odHodina.toIntOrNull() ?: 0,
    odDen = odDen.toIntOrNull() ?: 1,
    odMesiac = odMesiac.toIntOrNull() ?: 1,
    odRok = odRok.toIntOrNull() ?: 2021,
    doMinuta = doMinuta.toIntOrNull() ?: 0,
    doHodina = doHodina.toIntOrNull() ?: 0,
    doDen = doDen.toIntOrNull() ?: 1,
    doMesiac = doMesiac.toIntOrNull() ?: 1,
    doRok = doRok.toIntOrNull() ?: 2021,
    poznamka = poznamka,
    typ = typ
)

fun Udalost.toUdalostUiState(isEntryValid: Boolean = false): UdalostUiState = UdalostUiState(
    udalostDetails = this.toUdalostDetails(),
    isEntryValid = isEntryValid
)

fun Udalost.toUdalostDetails(): UdalostDetails =  UdalostDetails(
    id,
    nazov,
    odMinuta.toString(),
    odHodina.toString(),
    odDen.toString(),
    odMesiac.toString(),
    odRok.toString(),
    doMinuta.toString(),
    doHodina.toString(),
    doDen.toString(),
    doMesiac.toString(),
    doRok.toString(),
    poznamka
)
