package com.ivanovdev.data.mapper

import com.ivanovdev.data.source.local.database.currency.CurrencyEntity
import com.ivanovdev.domain.mapper.ModelMapper
import com.ivanovdev.domain.model.Currency
import javax.inject.Inject

class CurrencyModelMapperImpl @Inject constructor() : ModelMapper<CurrencyEntity, Currency> {
    override fun fromEntity(from: CurrencyEntity): Currency {
        return Currency(from.id, from.value, from.isFavourite)
    }

    override fun toEntity(from: Currency): CurrencyEntity {
        return CurrencyEntity(from.id, from.value, from.isFavorite)
    }
}