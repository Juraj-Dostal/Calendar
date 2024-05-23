package sk.duri.calendar.data

import kotlinx.coroutines.flow.Flow
import java.util.Date

class OfflineUdalostiRepository(private val udalostDao: UdalostDao) : UdalostiRepository {

    override fun getUdalostiVDni(date: Date): Flow<List<Udalost>> {
        //return udalostDao.getUdalostiVDni(date)
        throw NotImplementedError("Not implemented")
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