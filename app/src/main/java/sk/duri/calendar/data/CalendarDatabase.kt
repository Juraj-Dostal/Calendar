package sk.duri.calendar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Udalost::class], version = 1, exportSchema = false)
@TypeConverters(CalendarConverter::class)
abstract class CalendarDatabase : RoomDatabase(){

    abstract fun udalostDao(): UdalostDao

    companion object{
        @Volatile
        private var Instance: CalendarDatabase? = null

        fun getDatabase(content: Context): CalendarDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(content, CalendarDatabase::class.java, "calendar_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
