package com.tristanlarsin.currencyconverter.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tristanlarsin.currencyconverter.data.Converters
import com.tristanlarsin.currencyconverter.data.db.dao.ExchangeRateDao
import com.tristanlarsin.currencyconverter.data.db.entity.ExchangeRateEntity

@Database(
    entities = [ExchangeRateEntity::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(Converters::class)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun exchangeRateDao(): ExchangeRateDao

    companion object {
        @Volatile private var instance: CurrencyDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                CurrencyDatabase::class.java, "currency.db")
                .build()
    }
}