package com.ekenya.rnd.dashboard.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ekenya.rnd.common.model.ShipResponse
import com.ekenya.rnd.common.repo.ShipRepository
import com.ekenya.rnd.common.utils.Resource
import com.ekenya.rnd.dashboard.MockFileReader
import com.ekenya.rnd.dashboard.TestCoroutineRule
import com.ekenya.rnd.dashboard.ui.home.HomeViewModel
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HomeViewModelTest{
    @Rule
    @JvmField
    val instantRunExecutorRule = InstantTaskExecutorRule()
    @ExperimentalCoroutinesApi
    @get:Rule

    val testCoroutinesRule = TestCoroutineRule()
    private lateinit var homeViewModel : HomeViewModel
    private lateinit var shipRepository : ShipRepository
    private val response = MockFileReader("api.json").content

    @Before
    fun setUp(){
        shipRepository = mockk()
        homeViewModel = HomeViewModel(shipRepository)
    }
    @Test
    fun `for loading ships` (){
        val jsonResponse = Gson().fromJson(response, ShipResponse::class.java)
        val responseResource = Resource.success(jsonResponse)
        coEvery { shipRepository.getShips() } returns flow {
            emit(Resource.loading(null))
            emit(responseResource)
        }

        homeViewModel.getShips()
        val ships = homeViewModel.observeShipLiveData().value
        assertEquals(responseResource, ships)
    }
}