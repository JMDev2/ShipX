package com.ekenya.rnd.dashboard.di.injectables

import com.ekenya.rnd.baseapp.di.ModuleScope
import com.ekenya.rnd.common.Constants
import com.ekenya.rnd.common.api.ShipService
import com.ekenya.rnd.common.utils.NetworkInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {
    @Provides
    @ModuleScope
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @ModuleScope
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(GsonBuilder().setLenient().create())
    }

    @Provides
    @ModuleScope
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .addInterceptor(NetworkInterceptor())
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder().addHeader("User-Agent", "APP")
                        .build()
                )
            }.build()

    @Provides
    @ModuleScope
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()

    @Provides
    @ModuleScope
    fun provideShipApi(retrofit: Retrofit): ShipService =
        retrofit.create(ShipService::class.java)


}