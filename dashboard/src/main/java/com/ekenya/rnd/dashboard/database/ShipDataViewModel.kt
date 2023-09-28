package com.ekenya.rnd.dashboard.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekenya.rnd.common.model.ShipData
import com.ekenya.rnd.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShipDataViewModel @Inject constructor(private val shipDataRepository: ShipDataRepository): ViewModel() {

    val allShips : Flow<Resource<List<ShipData>>> = shipDataRepository.allShips.map {
        Resource.success(it)
    }

    //saving the ship
    fun saveShip(shipData: ShipData){
        viewModelScope.launch {
            shipDataRepository.saveShip(shipData)
        }
    }

}