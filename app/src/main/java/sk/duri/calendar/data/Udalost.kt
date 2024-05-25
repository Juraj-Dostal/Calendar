package sk.duri.calendar.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Calendar


@Entity(tableName = "udalost")
data class Udalost(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val dateFrom: Calendar,
    val dateTo: Calendar,
    val note: String,
    val typ: TypUdalosti
)

class CalendarConverter {
    @TypeConverter
    fun fromCalendar(calendar: Calendar): Long {
        return calendar.timeInMillis
    }

    @TypeConverter
    fun toCalendar(timeInMillis: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        return calendar
    }
}