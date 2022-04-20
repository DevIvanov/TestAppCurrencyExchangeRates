package com.ivanovdev.data.source.local.database.currency

import androidx.room.*
import com.ivanovdev.data.constants.LocalConstants.CURRENCY_DATABASE
import com.ivanovdev.data.constants.LocalConstants.CURRENCY_TABLE
import com.ivanovdev.data.constants.LocalConstants.ID
import com.ivanovdev.data.constants.LocalConstants.READ_QUERY
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Query(READ_QUERY)
    fun readAllData(): Flow<List<CurrencyEntity>>

    @Update
    fun update(item: CurrencyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<CurrencyEntity>)

    @Update(entity = CurrencyEntity::class)
    fun updateValues(list: List<UpdateCurrencyEntity>)
}