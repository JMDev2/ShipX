package com.ekenya.rnd.dashboard.di.injectables

import androidx.lifecycle.ViewModel
import com.ekenya.rnd.baseapp.di.ViewModelKey
import com.ekenya.rnd.dashboard.MainActivity
import com.ekenya.rnd.dashboard.ui.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class DashboardActivityModule {

    @ContributesAndroidInjector(modules = [HomeActivityModule::class])
    abstract fun contributeHomeActivity(): MainActivity

    @Module
    abstract class HomeActivityModule{
        @Binds
        @IntoMap
        @ViewModelKey(HomeViewModel::class)
        abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
    }


//    @ContributesAndroidInjector(modules = [DashboardMainActivityModule::class])
//    abstract fun contributeMainActivity(): MainActivity
//
//    @Module
//    abstract class DashboardMainActivityModule {
//        @Binds
//        @IntoMap
//        @ViewModelKey(HomeViewModel::class)
//        abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
//    }
}