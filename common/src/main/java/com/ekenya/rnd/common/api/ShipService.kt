package com.ekenya.rnd.common.api

import com.ekenya.rnd.common.model.ShipResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShipService {

    @GET("ships")
    suspend fun getAllShips()
    : Response<ShipResponse>
}