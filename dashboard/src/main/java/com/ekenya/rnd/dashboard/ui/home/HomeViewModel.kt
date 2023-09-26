package com.ekenya.rnd.dashboard.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekenya.rnd.common.model.ShipResponse
import com.ekenya.rnd.common.model.ShipResponseItem
import com.ekenya.rnd.common.repo.ShipRepository
import com.ekenya.rnd.common.utils.Resource
import com.ekenya.rnd.common.utils.SharedPreferencesManager
import com.ekenya.rnd.common.utils.SharedPreferencesManager.saveToSharedPreferences
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: ShipRepository) : ViewModel() {
    private var shipLiveData = MutableLiveData<Resource<ShipResponse?>>()

    private val _saveResult = MutableLiveData<Boolean>()
    val saveResult: LiveData<Boolean> = _saveResult

    private val _shipData = MutableLiveData<ShipResponseItem?>()
    val shipData: LiveData<ShipResponseItem?> = _shipData

    fun saveShipItems(context: Context, ship: ShipResponseItem) {
        val success = SharedPreferencesManager.saveToSharedPreferences(context, ship)
        _saveResult.value = success

        if (success) {
            // Log the data that was saved
            Log.d("YourViewModel", "Object saved successfully: $ship")
        } else {
            // Log a message when the save operation fails
            Log.e("YourViewModel", "Failed to save object")
        }
    }

    // Function to retrieve ship data
    fun retrieveShip(context: Context) {
        SharedPreferencesManager.retrieveShip(context) { shipResponseItem ->
            _shipData.value = shipResponseItem
        }
    }


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