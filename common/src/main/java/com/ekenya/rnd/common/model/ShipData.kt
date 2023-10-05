package com.ekenya.rnd.common.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "ship")
data class ShipData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var image: String? = null,
    var speed_kn: Int = 0,
    var weight_kg: Int = 0,
    var year_built: Int = 0,
    var ship_model: String? = null,
    var ship_name: String? = null,
    var ship_type: String? = null
)

fun ShipData.toShipResponseItem(): ShipResponseItem {
    return ShipResponseItem(
        0,
        false,
        0,
        image,
        speed_kn,
        weight_kg,
        0,
        year_built,
        "",
        ship_model,
        ship_name,
        ship_type
    )
}

fun ShipResponseItem.toShipData(): ShipData {
    return ShipData(
        0,
        image,
        speed_kn ?: 0,
        weight_kg ?: 0,
        year_built ?: 0,
        ship_model,
        ship_name,
        ship_type
    )
}
