package com.ivanovdev.test_app_currency_exchange_rates.di

import com.ivanovdev.data.repository.RemoteConstants.BASE_URL
import com.ivanovdev.data.source.remote.ExchangeRatesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideExchangeRatesApi(retrofit: Retrofit): ExchangeRatesApi =
        retrofit.create(ExchangeRatesApi::class.java)
}