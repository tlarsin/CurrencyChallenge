package com.tristanlarsin.currencyconverter.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tristanlarsin.currencyconverter.data.db.entity.ExchangeRateEntity

@Dao
interface ExchangeRateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(exchangeRates : ExchangeRateEntity)

    @Query("select * from exchange_rates")
    fun getExchangeRates(): LiveData<ExchangeRateEntity>
}