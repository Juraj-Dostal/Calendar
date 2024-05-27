package sk.duri.calendar.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UdalostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUdalost(udalost: Udalost)

    @Update
    suspend fun updateUdalost(udalost: Udalost)

    @Delete
    suspend fun deleteUdalost(udalost: Udalost)

    @Query("SELECT * FROM udalosti WHERE odDen = :denOd AND odMesiac = :mesiacOd AND odRok = :rokOd")
    fun getUdalostiVDni(denOd: Int, mesiacOd: Int, rokOd: Int): Flow<List<Udalost>>

    @Query("SELECT * FROM udalosti")
    fun getUdalosti(): Flow<List<Udalost>>
    @Query("SELECT * FROM udalosti WHERE id = :id")
    fun getUdalost(id: Int): Flow<Udalost?>
}