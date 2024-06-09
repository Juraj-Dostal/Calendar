package sk.duri.calendar.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "udalosti")
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
    Udalost("Udalosť"),
    Narodeniny("Narodeniny"),
    Praca("Práca"),
    Skola("Škola"),
    Dovolenka("Dovolenka"),
    Ostatne("Ostatné")
}