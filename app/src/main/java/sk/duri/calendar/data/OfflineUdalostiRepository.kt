package sk.duri.calendar.data

import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import java.util.Date

class OfflineUdalostiRepository(private val udalostDao: UdalostDao) : UdalostiRepository {

    override fun getUdalostiVDni(date: Calendar): Flow<List<Udalost>> {
        return udalostDao.getUdalostiVDni(date)
    }

    override fun getUdalost(id: Int): Flow<Udalost?> {
        return udalostDao.getUdalost(id)
    }

    override fun getUdalosti(): Flow<List<Udalost>> {
        return udalostDao.getUdalosti()
    }

    override suspend fun insertUdalost(udalost: Udalost) {
        udalostDao.insertUdalost(udalost)
    }

    override suspend fun updateUdalost(udalost: Udalost) {
        udalostDao.updateUdalost(udalost)
    }

    override suspend fun deleteUdalost(udalost: Udalost) {
        udalostDao.deleteUdalost(udalost)
    }


}