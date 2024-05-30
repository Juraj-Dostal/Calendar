package sk.duri.calendar.ui.nameDayEdit

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONObject

class NameDayEditViewModel(val application: Application) : ViewModel() {
    private var isChanged = false
    private var isAllCheck = true
    private val isExist = application.fileList().contains("custom_name_day.json")

    val assetManager = application.assets
    val inputStream = assetManager.open("slovak-name-day.json")
    val nameDayJson = JSONObject(inputStream.bufferedReader().use { it.readText() })
    var checkNameDay : JSONObject? = null

    val nameDayListUiState: MutableStateFlow<NameDayListUiState> = MutableStateFlow(NameDayListUiState())

    init {
        setJson()

        val nameDayList = mutableListOf<NameDayCheckBox>()

        nameDayJson.keys().forEach { month ->
            val monthJson = nameDayJson.getJSONObject(month)
            monthJson.keys().forEach { day ->
                val name = monthJson.getString(day)
                nameDayList.add(NameDayCheckBox(month.toInt(), day.toInt(), name,
                    if (checkNameDay != null) {
                        if (checkNameDay!!.has(month) &&
                            checkNameDay!!.getJSONObject(month).has(day) &&
                            checkNameDay!!.getJSONObject(month).getString(day) == name) {
                            mutableStateOf(true)
                        } else {
                            isAllCheck = false
                            mutableStateOf(false)
                        }
                    } else {
                        mutableStateOf(true)
                    }
                )
                )
            }
        }

        nameDayList.sortBy { it.name }

        this.nameDayListUiState.value = NameDayListUiState(nameDayList)
    }

    fun checkAll() {
        if (isAllCheck){
            isAllCheck = false
            isChanged = true
            nameDayListUiState.value.nameDayList.forEach {
                it.isChecked.value = false
            }
        } else{
            isAllCheck = true
            isChanged = true
            nameDayListUiState.value.nameDayList.forEach {
                it.isChecked.value = true
            }
        }
    }

     fun save() {
        if (isChanged) {
            saveJsonToFile(application)
        }
    }

    private fun setJson() {
        checkNameDay = if (isExist) {
            val inputStream = application.openFileInput("custom_name_day.json")
            JSONObject(inputStream.bufferedReader().use { it.readText() })
        } else {
            null
        }
    }

    fun changeIsChecked(nameDayCheckBox: NameDayCheckBox) {
        isChanged = true
        nameDayCheckBox.isChecked.value = !nameDayCheckBox.isChecked.value
    }

    private fun createJsonFromList(): JSONObject {
        val json = JSONObject()
        // put to json 12 months JSONObject
        for (i in 0..11) {
            json.put(i.toString(), JSONObject())
        }

        val list =  nameDayListUiState.value.nameDayList
        list.filter { it.isChecked.value }.forEach { nameDay ->
            val month = json.optJSONObject(nameDay.month.toString()) ?: JSONObject()
            month.put(nameDay.day.toString(), nameDay.name)
            json.put(nameDay.month.toString(), month)
        }

        return json
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