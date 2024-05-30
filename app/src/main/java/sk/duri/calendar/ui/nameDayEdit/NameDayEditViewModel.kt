package sk.duri.calendar.ui.nameDayEdit

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONObject

class NameDayEditViewModel(application: Application) : ViewModel() {
    private var isChanged = 0
    private val assetManager = application.assets
    private val inputStream = assetManager.open("slovak-name-day.json")
    private val nameDayJson = JSONObject(inputStream.bufferedReader().use { it.readText() })

    val nameDayListUiState: MutableStateFlow<NameDayListUiState> = MutableStateFlow(NameDayListUiState())

    init {
        val nameDayList = mutableListOf<NameDayCheckBox>()

        nameDayJson.keys().forEach { month ->
            val monthJson = nameDayJson.getJSONObject(month)
            monthJson.keys().forEach { day ->
                val name = monthJson.getString(day)
                nameDayList.add(NameDayCheckBox(month.toInt(), day.toInt(), name, mutableStateOf(true)))
            }
        }

        nameDayList.sortBy { it.name }

        this.nameDayListUiState.value = NameDayListUiState(nameDayList)
    }

    fun changeIsChecked(nameDayCheckBox: NameDayCheckBox) {
        if (nameDayCheckBox.isChecked.value) {
            isChanged--
        } else {
            isChanged++
        }
        nameDayCheckBox.isChecked.value = !nameDayCheckBox.isChecked.value
    }

    private fun createJsonFromList(): JSONObject {
        val json = JSONObject()
        val list =  nameDayListUiState.value.nameDayList
        list.filter { it.isChecked.value }.forEach { nameDay ->
            val month = json.optJSONObject(nameDay.month.toString()) ?: JSONObject()
            month.put(nameDay.day.toString(), nameDay.name)
            json.put(nameDay.month.toString(), month)
        }

        return json
    }

    fun readJsonFromFile(context: Context, fileName: String): JSONObject? {


        return try {
            val inputStream = context.openFileInput(fileName)
            val json = JSONObject(inputStream.bufferedReader().use { it.readText() })
            json
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

     fun saveJsonToFile(context: Context){
         val json = createJsonFromList()
         val fileName = "custom_name_day.json"
         context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
             it.write(json.toString().toByteArray())
         }

    }
}

data class NameDayListUiState(
    val nameDayList: List<NameDayCheckBox> = listOf()
)


data class NameDayCheckBox(
    val month: Int,
    val day: Int,
    val name: String,
    var isChecked: MutableState<Boolean>
)