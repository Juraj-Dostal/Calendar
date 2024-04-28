package sk.duri.calendar.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalTime
import java.util.Date

@Entity(tableName = "udalost")
data class Udalost(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val date: Date,
    val from: LocalTime,
    val to: LocalTime,
    val poznamka: String,
    val typ: TypUdalosti
)
