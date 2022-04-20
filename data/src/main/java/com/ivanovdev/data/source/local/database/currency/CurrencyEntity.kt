package com.ivanovdev.data.source.local.database.currency

import androidx.room.*
import com.ivanovdev.data.constants.LocalConstants.CURRENCY_TABLE
import com.ivanovdev.data.constants.LocalConstants.ID

@Entity(tableName = CURRENCY_TABLE,
    indices = [Index(value = [ID], unique = true)])
data class CurrencyEntity (
    @PrimaryKey
    val id: String = "",
    val value: Double,
    val isFavourite: Boolean
)