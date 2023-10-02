package com.ekenya.rnd.dashboard.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ekenya.rnd.common.model.ShipData


@Database(entities = [ShipData::class], version = 2, exportSchema = false)
abstract class ShipDatabase: RoomDatabase() {

    abstract fun shipDao(): ShipDao


    companion object{
        private var INSTANCE: ShipDatabase? = null

        fun getInstance(context: Context): ShipDatabase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ShipDatabase::class.java,
                        "shipinformation"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}