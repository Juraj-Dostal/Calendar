package sk.duri.calendar.data
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import java.util.Date

interface UdalostiRepository {

    fun getUdalostiVDni(denOd: Int, mesiacOd: Int, rokOd: Int): Flow<List<Udalost>>

    fun getUdalost(id: Int): Flow<Udalost?>

    fun getUdalosti(): Flow<List<Udalost>>

    suspend fun insertUdalost(udalost: Udalost)

    suspend fun updateUdalost(udalost: Udalost)

    suspend fun deleteUdalost(udalost: Udalost)
}