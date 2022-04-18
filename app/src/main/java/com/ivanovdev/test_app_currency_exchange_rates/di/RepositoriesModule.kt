package com.ivanovdev.test_app_currency_exchange_rates.di

import com.ivanovdev.data.repository.RatesRepositoryImpl
import com.ivanovdev.data.source.remote.RemoteDataSource
import com.ivanovdev.domain.repository.RatesRepository
import com.ivanovdev.domain.usecase.RatesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    fun provideRatesRepository(remoteDataSource: RemoteDataSource): RatesRepository =
        RatesRepositoryImpl(remoteDataSource)

    @Provides
    fun provideRates(repository: RatesRepository): RatesUseCase = RatesUseCase(repository)
}