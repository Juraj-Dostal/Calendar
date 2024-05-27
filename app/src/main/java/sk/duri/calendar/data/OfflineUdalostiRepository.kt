package sk.duri.calendar.data

import kotlinx.coroutines.flow.Flow

class OfflineUdalostiRepository(private val udalostDao: UdalostDao) : UdalostiRepository {

    override fun getUdalostiVDni(denOd: Int, mesiacOd: Int, rokOd: Int): Flow<List<Udalost>> = udalostDao.getUdalostiVDni(denOd, mesiacOd, rokOd)

    override fun getUdalost(id: Int): Flow<Udalost?> = udalostDao.getUdalost(id)

    override fun getUdalosti(): Flow<List<Udalost>> = udalostDao.getUdalosti()

    override suspend fun insertUdalost(udalost: Udalost) = udalostDao.insertUdalost(udalost)

    override suspend fun updateUdalost(udalost: Udalost) = udalostDao.updateUdalost(udalost)
    override suspend fun deleteUdalost(udalost: Udalost) = udalostDao.deleteUdalost(udalost)
}