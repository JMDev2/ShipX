package com.ekenya.rnd.common.api

import com.ekenya.rnd.common.model.ShipResponse
import com.ekenya.rnd.common.utils.Resource
import javax.inject.Inject

class ShipImpl @Inject constructor(private val api: ShipService) {

    suspend fun getShips(): Resource<ShipResponse?>{
        val response = api.getAllShips()
        return if (response.isSuccessful){
            Resource.success(response.body())
        }else{
            Resource.error("No ships found", null)
        }
    }

}