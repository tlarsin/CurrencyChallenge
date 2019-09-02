package com.tristanlarsin.currencyconverter.repository.converter

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

const val SELECTED_CURRENCY_NAME = "SELECTED CURRENCY NAME"
const val SELECTED_CURRENCY_RATE = "SELECTED CURRENCY RATE"

class ConverterRepositoryImpl(context: Context) : ConverterRepository {
    private val appContext = context.applicationContext

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun getChosenExchangeRate(): Map<String, Double> {
        val selectedName = preferences.getString(SELECTED_CURRENCY_NAME, "EUR")
        val selectedRate = preferences.getFloat(SELECTED_CURRENCY_RATE, 0f)

        return mapOf(Pair(selectedName, selectedRate.toDouble()))
    }
}