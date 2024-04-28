package sk.duri.calendar.data
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface UdalostiRepository {

    fun getUdalostiVDni(date: Date): Flow<List<Udalost>>

    suspend fun insertUdalost(udalost: Udalost)

    suspend fun updateUdalost(udalost: Udalost)

    suspend fun deleteUdalost(udalost: Udalost)
}