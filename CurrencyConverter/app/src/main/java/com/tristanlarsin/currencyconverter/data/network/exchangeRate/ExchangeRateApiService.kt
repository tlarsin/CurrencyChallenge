package com.tristanlarsin.currencyconverter.data.network.exchangeRate

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tristanlarsin.currencyconverter.BuildConfig
import com.tristanlarsin.currencyconverter.data.db.entity.ExchangeRateEntity
import com.tristanlarsin.currencyconverter.data.network.ConnectivityInterceptor
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ExchangeRateApiService {

    @GET("api/latest")
    fun getExchangeRates(): Deferred<ExchangeRateEntity>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): ExchangeRateApiService {
            val requestIntercepter = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_key", BuildConfig.FixerAPIKey)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestIntercepter)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.FixerBaseURL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ExchangeRateApiService::class.java)
        }
    }
}