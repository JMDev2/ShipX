package com.ekenya.rnd.common.utils

import android.content.Context
import android.content.SharedPreferences
import com.ekenya.rnd.common.model.ShipResponseItem
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

object SharedPreferencesManager {

    const val SHIP_DATA_KEY = "shipData"


    fun saveToSharedPreferences(context: Context, ship: ShipResponseItem): Boolean {
        val gson = Gson()
        val shipJson = gson.toJson(ship)

        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("shipData", shipJson)
        return editor.commit() // This returns true if the save operation was successful
    }


    fun retrieveShip(context: Context, callback: (ShipResponseItem?) -> Unit) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val shipJson = sharedPreferences.getString(SHIP_DATA_KEY, null)

        if (shipJson != null) {
            val gson = Gson()
            val shipResponseItem = gson.fromJson(shipJson, ShipResponseItem::class.java)
            callback(shipResponseItem)
        } else {
            callback(null) // Handle the case where ship data is not found
        }
    }
}



