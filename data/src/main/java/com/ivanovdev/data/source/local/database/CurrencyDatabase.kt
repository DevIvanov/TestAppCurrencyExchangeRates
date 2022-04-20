package com.ivanovdev.data.source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ivanovdev.data.constants.LocalConstants.CURRENCY_DATABASE
import com.ivanovdev.data.source.local.database.currency.CurrencyDao
import com.ivanovdev.data.source.local.database.currency.CurrencyEntity


@Database(entities = [CurrencyEntity::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    companion object {
        @Volatile
        var INSTANCE: CurrencyDatabase? = null

        fun getDatabase(context: Context): CurrencyDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    CurrencyDatabase::class.java,
                    CURRENCY_DATABASE
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}