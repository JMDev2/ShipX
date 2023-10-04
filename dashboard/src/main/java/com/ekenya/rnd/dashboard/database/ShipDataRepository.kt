package com.ekenya.rnd.dashboard.database

import android.text.BoringLayout
import com.ekenya.rnd.common.model.ShipData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShipDataRepository @Inject constructor(private val shipDao: ShipDao) {

    val allShips: Flow<List<ShipData>> = shipDao.getSavedShip()

    // Save ship in a coroutine
    suspend fun saveShip(shipData: ShipData) {
        withContext(Dispatchers.IO) {
            shipDao.saveShip(shipData)
        }
    }

    // Delete ship in a coroutine
    suspend fun delete(shipData: ShipData) {
        withContext(Dispatchers.IO) {
            shipDao.delete(shipData)
        }
    }

    suspend fun doesShipExist(shipId: Long): Boolean{
        val count = shipDao.getShipCount(shipId)
        return count > 0
    }
}