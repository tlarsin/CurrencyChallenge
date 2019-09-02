package com.tristanlarsin.currencyconverter.ui.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tristanlarsin.veox.data.repository.exchangeRate.ExchangeRateRepository

class CurrencyViewModelFactory(
    private val exchangeRateRepository: ExchangeRateRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrencyViewModel(exchangeRateRepository) as  T
    }
}