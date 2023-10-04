package com.ekenya.rnd.dashboard.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekenya.rnd.common.model.ShipData
import com.ekenya.rnd.common.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShipDataViewModel @Inject constructor(private val shipDataRepository: ShipDataRepository) : ViewModel() {

    val allShips: Flow<Resource<List<ShipData>>> = shipDataRepository.allShips.map {
        Resource.success(it)
    }

    // Saving a ship
    fun saveShip(shipData: ShipData) {
        viewModelScope.launch {
            shipDataRepository.saveShip(shipData)
        }
    }

    // Deleting a ship
    fun delete(shipData: ShipData) {
        viewModelScope.launch {
            shipDataRepository.delete(shipData)
        }
    }

    suspend fun checkIfShipExists(shipId: Long): Boolean{
        return withContext(Dispatchers.IO){
        shipDataRepository.doesShipExist(shipId)
            }
    }
}