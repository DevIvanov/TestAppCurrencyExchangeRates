package com.ivanovdev.domain.repository

import com.ivanovdev.domain.model.Currency
import com.ivanovdev.domain.model.UpdateCurrency
import kotlinx.coroutines.flow.Flow

interface CurrencyDBRepository {
    fun readAll(): Flow<List<Currency>>
    suspend fun update(item: Currency)
    suspend fun insertAll(list: List<Currency>)
    suspend fun updateValues(list: List<UpdateCurrency>)
}