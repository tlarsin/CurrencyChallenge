package com.tristanlarsin.currencyconverter.data.network.exchangeRate

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tristanlarsin.currencyconverter.data.db.entity.ExchangeRateEntity
import com.tristanlarsin.currencyconverter.internal.NoConnectivityException

class ExchangeRateNetworkDataSourceImpl(private val apiService: ExchangeRateApiService) :
    ExchangeRateNetworkDataSource {

    private val _downloadedExchangeRateList = MutableLiveData<ExchangeRateEntity>()

    override val downloadedExchangeRateList: LiveData<ExchangeRateEntity>
        get() = _downloadedExchangeRateList

    override suspend fun fetchExchangeRates(base: String) {
        try {
            val fetchExchangeRateList = apiService
                .getExchangeRates()
                .await()
            _downloadedExchangeRateList.postValue(fetchExchangeRateList)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)

        }
    }
}