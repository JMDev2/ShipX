package com.ekenya.rnd.common.repo

import com.ekenya.rnd.common.api.ShipImpl
import com.ekenya.rnd.common.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ShipRepository @Inject constructor(private val api : ShipImpl) {
    suspend fun getShips() = flow {
        emit(Resource.loading(null))
        emit(api.getShips())
    }.flowOn(Dispatchers.IO)
}