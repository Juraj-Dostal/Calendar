package sk.duri.calendar.data

import android.content.Context
import sk.duri.calendar.data.CalendarDatabase.Companion.getDatabase

interface AppContainer {
    val udalostiRepository: UdalostiRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val udalostiRepository: UdalostiRepository by lazy {
        OfflineUdalostiRepository(CalendarDatabase.getDatabase(context).udalostDao())
    }

}