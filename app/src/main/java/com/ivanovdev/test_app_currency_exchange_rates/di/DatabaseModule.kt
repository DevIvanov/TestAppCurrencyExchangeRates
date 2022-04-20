package com.ivanovdev.test_app_currency_exchange_rates.di

import android.content.Context
import com.ivanovdev.data.mapper.CurrencyModelMapperImpl
import com.ivanovdev.data.mapper.UpdateCurrencyModelMapperImpl
import com.ivanovdev.data.repository.CurrencyDBRepositoryImpl
import com.ivanovdev.data.source.local.database.CurrencyDatabase
import com.ivanovdev.data.source.local.database.currency.CurrencyDao
import com.ivanovdev.domain.repository.CurrencyDBRepository
import com.ivanovdev.domain.usecase.CurrencyDBUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun getRoomDbInstance(@ApplicationContext context: Context): CurrencyDatabase {
        return CurrencyDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun getCurrencyDao(roomDatabase: CurrencyDatabase): CurrencyDao {
        return roomDatabase.currencyDao()
    }

    @Singleton
    @Provides
    fun getCurrencyDBRepository(
        roomDatabase: CurrencyDatabase,
        mapperImpl: CurrencyModelMapperImpl,
        updateMapperImpl: UpdateCurrencyModelMapperImpl
    ): CurrencyDBRepository {
        return CurrencyDBRepositoryImpl(roomDatabase.currencyDao(), mapperImpl, updateMapperImpl)
    }

    @Provides
    fun provideCurrencyDBUseCase(repository: CurrencyDBRepository): CurrencyDBUseCase =
        CurrencyDBUseCase(repository)
}