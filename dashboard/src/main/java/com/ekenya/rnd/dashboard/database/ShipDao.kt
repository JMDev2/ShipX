package com.ekenya.rnd.dashboard.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ekenya.rnd.common.model.ShipData
import kotlinx.coroutines.flow.Flow

@Dao
interface ShipDao {
    @Insert
    suspend fun saveShip(shipData : ShipData)

    @Query("SELECT * FROM ship")
    fun getSavedShip(): Flow<List<ShipData>>

    @Delete
    fun delete(shipData: ShipData)
}