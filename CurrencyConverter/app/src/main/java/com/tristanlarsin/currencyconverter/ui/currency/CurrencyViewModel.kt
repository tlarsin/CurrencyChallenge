package com.tristanlarsin.currencyconverter.ui.currency

import androidx.lifecycle.ViewModel
import com.tristanlarsin.currencyconverter.internal.lazyDeferred
import com.tristanlarsin.veox.data.repository.exchangeRate.ExchangeRateRepository

class CurrencyViewModel(
    private val exchangeRateRepository: ExchangeRateRepository
) : ViewModel() {

    val exchangeRates by lazyDeferred {
        exchangeRateRepository.getExchangeRate()
    }

    fun setSelectedRate(item: Map<String, Double>) {
        exchangeRateRepository.setSelectedRate(item)
    }
}