package com.ekenya.rnd.dashboard.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekenya.rnd.common.model.ShipResponse
import com.ekenya.rnd.common.repo.ShipRepository
import com.ekenya.rnd.common.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: ShipRepository) : ViewModel() {
    var shipLiveData = MutableLiveData<Resource<ShipResponse?>>()

    private val _saveResult = MutableLiveData<Boolean>()
    val saveResult: LiveData<Boolean> = _saveResult


    init {
        getShips()
    }

    fun getShips() = viewModelScope.launch {
        repository.getShips().collect {
            shipLiveData.postValue(it)
        }
    }

    fun observeShipLiveData(): LiveData<Resource<ShipResponse?>> {
        return shipLiveData
    }

    fun refresh() {
        getShips()
    }
}