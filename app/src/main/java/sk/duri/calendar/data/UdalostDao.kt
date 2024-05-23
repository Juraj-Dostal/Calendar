package sk.duri.calendar.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface UdalostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUdalost(udalost: Udalost)

    @Update
    suspend fun updateUdalost(udalost: Udalost)

    @Delete
    suspend fun deleteUdalost(udalost: Udalost)

    //@Query("SELECT * FROM udalost WHERE date = :date")
    //fun getUdalostiVDni(date: Date): Flow<List<Udalost>>
}