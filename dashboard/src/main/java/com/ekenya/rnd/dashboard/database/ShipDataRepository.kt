package com.ekenya.rnd.dashboard.database

import com.ekenya.rnd.common.model.ShipData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShipDataRepository @Inject constructor(private val shipDao: ShipDao) {

    val allShips : Flow<List<ShipData>> = shipDao.getSavedShip()

    //save ship
    suspend fun saveShip(shipData: ShipData){
        shipDao.saveShip(shipData)
    }
}