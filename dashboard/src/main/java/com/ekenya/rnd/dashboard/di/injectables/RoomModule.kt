package com.ekenya.rnd.dashboard.di.injectables

import android.content.Context
import com.ekenya.rnd.baseapp.di.ModuleScope
import com.ekenya.rnd.common.model.ShipData
import com.ekenya.rnd.dashboard.database.ShipDao
import com.ekenya.rnd.dashboard.database.ShipDatabase
import dagger.Module
import dagger.Provides

@Module
class RoomModule(private val context: Context) {

    @Provides
    @ModuleScope
    fun provideContext(): Context{
        return context
    }

    @Provides
    @ModuleScope
    fun provideAppDatabase(context: Context): ShipDatabase{
        return ShipDatabase.getInstance(context)
    }

    @Provides
    @ModuleScope
    fun provideShipDao(database: ShipDatabase): ShipDao {
        return database.shipDao()
    }
}