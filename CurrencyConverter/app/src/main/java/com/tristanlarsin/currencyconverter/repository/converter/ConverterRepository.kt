package com.tristanlarsin.currencyconverter.repository.converter

import android.app.Activity

interface ConverterRepository {
    fun getChosenExchangeRate(): Map<String, Double>
}