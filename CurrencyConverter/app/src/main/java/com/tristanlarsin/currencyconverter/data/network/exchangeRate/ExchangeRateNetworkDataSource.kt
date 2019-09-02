package com.tristanlarsin.currencyconverter.data.network.exchangeRate

import androidx.lifecycle.LiveData
import com.tristanlarsin.currencyconverter.data.db.entity.ExchangeRateEntity

interface ExchangeRateNetworkDataSource {
    val downloadedExchangeRateList: LiveData<ExchangeRateEntity>

    suspend fun fetchExchangeRates(
        base: String
    )
}