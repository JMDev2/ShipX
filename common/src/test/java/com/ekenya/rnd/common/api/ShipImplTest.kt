package com.ekenya.rnd.common.api

import com.ekenya.rnd.common.MockFileReader
import com.ekenya.rnd.common.utils.Status
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class ShipImplTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var shipImpl: ShipImpl
    private var okHttpClient = OkHttpClient.Builder()
        .build()

    private val response = MockFileReader("api.json").content


    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val shipApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShipService::class.java)
        shipImpl = ShipImpl(shipApi)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


    @Test
    fun `for multiple api, api must return respone` () = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(response)
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = shipImpl.getShips()

        assertEquals(Status.SUCCESS, actualResponse.status)
    }

    @Test
    fun `respond server error, 500` () = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = shipImpl.getShips()
        assertEquals(Status.ERROR, actualResponse.status)
    }
}