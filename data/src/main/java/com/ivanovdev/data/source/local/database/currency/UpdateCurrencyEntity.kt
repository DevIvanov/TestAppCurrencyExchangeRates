package com.ivanovdev.data.source.local.database.currency

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UpdateCurrencyEntity (
    @PrimaryKey
    val id: String = "",
    val value: Double
)
