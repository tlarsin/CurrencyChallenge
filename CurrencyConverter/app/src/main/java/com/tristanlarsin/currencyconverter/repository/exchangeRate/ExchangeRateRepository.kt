package com.tristanlarsin.veox.data.repository.exchangeRate

import androidx.lifecycle.LiveData
import com.tristanlarsin.currencyconverter.data.db.entity.ExchangeRateEntity

interface ExchangeRateRepository {
    suspend fun getExchangeRate(): LiveData<out ExchangeRateEntity>

    fun setSelectedRate(item: Map<String, Double>)
}