package sk.duri.calendar.ui

import android.app.Application
import sk.duri.calendar.data.AppContainer
import sk.duri.calendar.data.AppDataContainer

class UdalostiApplication : Application(){

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}