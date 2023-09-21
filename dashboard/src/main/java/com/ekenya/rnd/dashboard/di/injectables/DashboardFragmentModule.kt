package com.ekenya.rnd.dashboard.di.injectables

import androidx.lifecycle.ViewModel
import com.ekenya.rnd.baseapp.di.ViewModelKey
import com.ekenya.rnd.dashboard.ui.home.HomeFragment
import com.ekenya.rnd.dashboard.ui.home.HomeViewModel
import com.ekenya.rnd.dashboard.ui.home.ShipDetailsFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class DashboardFragmentModule {

    //dashboard fragment
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun contributeHomeFragment(): HomeFragment
    @Module
    abstract class HomeFragmentModule {
        @Binds
        @IntoMap
        @ViewModelKey(HomeViewModel::class)
        abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
    }

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun contributeShipDetailsFragment(): ShipDetailsFragment

}