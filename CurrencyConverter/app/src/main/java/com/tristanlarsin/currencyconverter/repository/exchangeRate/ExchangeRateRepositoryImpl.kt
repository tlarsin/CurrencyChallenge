package com.tristanlarsin.veox.data.repository.exchangeRate

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import com.tristanlarsin.currencyconverter.data.db.dao.ExchangeRateDao
import com.tristanlarsin.currencyconverter.data.db.entity.ExchangeRateEntity
import com.tristanlarsin.currencyconverter.data.network.exchangeRate.ExchangeRateNetworkDataSource
import com.tristanlarsin.currencyconverter.repository.converter.SELECTED_CURRENCY_NAME
import com.tristanlarsin.currencyconverter.repository.converter.SELECTED_CURRENCY_RATE

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ExchangeRateRepositoryImpl(private val context: Context,
                                 private val exchangeRateDao: ExchangeRateDao,
                                 private val exchangeRateNetworkDataSource: ExchangeRateNetworkDataSource
) : ExchangeRateRepository {

    private val appContext = context.applicationContext

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)


    override fun setSelectedRate(item: Map<String, Double>) {
        preferences.edit().putString(SELECTED_CURRENCY_NAME, item.keys.toTypedArray()[0]).apply()
        preferences.edit().putFloat(SELECTED_CURRENCY_RATE, item.values.toTypedArray()[0].toFloat()).apply()
    }

    init {
        exchangeRateNetworkDataSource.downloadedExchangeRateList.observeForever {
            persistFetchedExchangeRates(it)
        }
    }

    override suspend fun getExchangeRate(): LiveData<out ExchangeRateEntity> {
        initExchangeRate()
        return withContext(Dispatchers.IO) { exchangeRateDao.getExchangeRates() }
    }

    private fun persistFetchedExchangeRates(exchangeRates: ExchangeRateEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            exchangeRateDao.upsert(exchangeRates)
        }
    }

    private suspend fun initExchangeRate() {
        if(isFetchExchangeNeeded(ZonedDateTime.now().minusHours(1))) {
            fetchExchangeRate()
        } else {

        }
    }

    private suspend fun fetchExchangeRate() {
        exchangeRateNetworkDataSource.fetchExchangeRates("")
    }

    private fun isFetchExchangeNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}