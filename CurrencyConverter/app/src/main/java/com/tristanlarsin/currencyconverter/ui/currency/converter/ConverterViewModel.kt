package com.tristanlarsin.currencyconverter.ui.currency.converter

import androidx.lifecycle.ViewModel
import com.tristanlarsin.currencyconverter.internal.lazyDeferred
import com.tristanlarsin.currencyconverter.repository.converter.ConverterRepository

class ConverterViewModel(
    private val converterRepository: ConverterRepository
) : ViewModel() {

    fun getSelectedCurrency(): Map<String, Double> {
        return converterRepository.getChosenExchangeRate()
    }
}