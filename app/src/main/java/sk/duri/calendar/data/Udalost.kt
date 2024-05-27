package sk.duri.calendar.data

import androidx.compose.ui.res.stringResource
import androidx.room.Entity
import androidx.room.PrimaryKey
import sk.duri.calendar.R

@Entity(tableName = "udalost")
data class Udalost(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nazov: String,
    val odMinuta: Int,
    val odHodina: Int,
    val odDen: Int,
    val odMesiac: Int,
    val odRok: Int,
    val doMinuta: Int,
    val doHodina: Int,
    val doDen: Int,
    val doMesiac: Int,
    val doRok: Int,
    val poznamka: String,
    val typ: TypUdalosti
)

enum class TypUdalosti(val nazov: String) {
    Udalost("Event"),
    Narodeniny("Bitrhday"),
    Meniny("Name day"),
    Sviatok("Holiday"),
    Praca("Work"),
    Skola("School"),
    Dovolenka("Vacation"),
    Ostatne("Other")
}