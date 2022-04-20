package com.ivanovdev.domain.usecase

import com.ivanovdev.domain.model.Currency
import com.ivanovdev.domain.model.UpdateCurrency
import com.ivanovdev.domain.repository.CurrencyDBRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CurrencyDBUseCase @Inject constructor(private val repository: CurrencyDBRepository) {
    fun readAll(): Flow<List<Currency>> {
        return repository.readAll()
    }

    suspend fun update (item: Currency) {
        repository.update(item)
    }

    suspend fun insertAll(list: List<Currency>) {
        repository.insertAll(list)
    }

    suspend fun updateValues (list: List<UpdateCurrency>) {
        repository.updateValues(list)
    }}