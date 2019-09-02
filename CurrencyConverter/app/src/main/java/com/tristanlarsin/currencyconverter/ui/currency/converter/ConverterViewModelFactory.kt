package com.tristanlarsin.currencyconverter.ui.currency.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tristanlarsin.currencyconverter.repository.converter.ConverterRepository
import com.tristanlarsin.currencyconverter.ui.currency.CurrencyViewModel

class ConverterViewModelFactory(
    private val converterRepository: ConverterRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ConverterViewModel(converterRepository) as  T
    }
}