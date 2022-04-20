package com.ivanovdev.data.mapper

import com.ivanovdev.data.source.local.database.currency.UpdateCurrencyEntity
import com.ivanovdev.domain.mapper.ModelMapper
import com.ivanovdev.domain.model.UpdateCurrency
import javax.inject.Inject

class UpdateCurrencyModelMapperImpl @Inject constructor() :
    ModelMapper<UpdateCurrencyEntity, UpdateCurrency> {
    override fun fromEntity(from: UpdateCurrencyEntity): UpdateCurrency {
        return UpdateCurrency(from.id, from.value)
    }

    override fun toEntity(from: UpdateCurrency): UpdateCurrencyEntity {
        return UpdateCurrencyEntity(from.id, from.value)
    }
}