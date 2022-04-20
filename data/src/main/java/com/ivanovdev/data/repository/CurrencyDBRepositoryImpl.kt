package com.ivanovdev.data.repository

import com.ivanovdev.data.mapper.CurrencyModelMapperImpl
import com.ivanovdev.data.mapper.UpdateCurrencyModelMapperImpl
import com.ivanovdev.data.source.local.database.currency.CurrencyDao
import com.ivanovdev.domain.model.Currency
import com.ivanovdev.domain.model.UpdateCurrency
import com.ivanovdev.domain.repository.CurrencyDBRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrencyDBRepositoryImpl @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val mapper: CurrencyModelMapperImpl,
    private val updateMapper: UpdateCurrencyModelMapperImpl
) : CurrencyDBRepository {


    override fun readAll(): Flow<List<Currency>> {
        return currencyDao.readAllData().map { it.map(mapper::fromEntity) }
    }

    override suspend fun update(item: Currency) {
        return currencyDao.update(mapper.toEntity(item))
    }

    override suspend fun insertAll(list: List<Currency>) {
        currencyDao.insertAll(list.map { mapper.toEntity(it) })
    }

    override suspend fun updateValues(list: List<UpdateCurrency>) {
        currencyDao.updateValues(list.map { updateMapper.toEntity(it) })
    }
}