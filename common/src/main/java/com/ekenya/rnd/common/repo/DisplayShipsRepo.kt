package com.ekenya.rnd.common.repo

import android.content.Context
import com.ekenya.rnd.common.model.ShipResponseItem
import com.ekenya.rnd.common.utils.SharedPreferencesManager

class DisplayShipsRepo(private val sharedPreferencesManager: SharedPreferencesManager) {

    // Save ship data to SharedPreferences
//    fun saveShip(context: Context, ship: ShipResponseItem) {
//        sharedPreferencesManager.saveToSharedPreferences(context, ship)
//    }

    // Retrieve ship data from SharedPreferences
    fun getShip(context: Context, callback: (ShipResponseItem?) -> Unit) {
        sharedPreferencesManager.retrieveShip(context) { shipResponseItem ->
            callback(shipResponseItem)
        }
    }
}