package com.ekenya.rnd.common.model

import android.os.Parcel
import android.os.Parcelable

class ShipResponse : ArrayList<ShipResponseItem>()

class ShipResponseItem(
    val abs: Int,
    val active: Boolean,

    val `class`: Int,

    val image: String?,
    val speed_kn: Int?,
    val weight_kg: Int?,
    val weight_lbs: Int?,
    val year_built: Int?,

    val ship_id: String?,
    val ship_model: String?,
    val ship_name: String?,
    val ship_type: String?,

    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(abs)
        parcel.writeByte(if (active) 1 else 0)
        parcel.writeString(image)
        parcel.writeValue(speed_kn)
        parcel.writeValue(weight_kg)
        parcel.writeValue(weight_lbs)
        parcel.writeValue(year_built)
        parcel.writeString(ship_id)
        parcel.writeString(ship_model)
        parcel.writeString(ship_name)
        parcel.writeString(ship_type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShipResponseItem> {
        override fun createFromParcel(parcel: Parcel): ShipResponseItem {
            return ShipResponseItem(parcel)
        }

        override fun newArray(size: Int): Array<ShipResponseItem?> {
            return arrayOfNulls(size)
        }
    }
}

class Mission(
    val flight: Int,
    val name: String
)