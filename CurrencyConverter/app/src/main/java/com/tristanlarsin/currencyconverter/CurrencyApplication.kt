package com.tristanlarsin.currencyconverter

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.tristanlarsin.currencyconverter.data.db.CurrencyDatabase
import com.tristanlarsin.currencyconverter.data.network.ConnectivityInterceptor
import com.tristanlarsin.currencyconverter.data.network.ConnectivityInterceptorImpl
import com.tristanlarsin.currencyconverter.data.network.exchangeRate.ExchangeRateApiService
import com.tristanlarsin.currencyconverter.data.network.exchangeRate.ExchangeRateNetworkDataSource
import com.tristanlarsin.currencyconverter.data.network.exchangeRate.ExchangeRateNetworkDataSourceImpl
import com.tristanlarsin.currencyconverter.repository.converter.ConverterRepository
import com.tristanlarsin.currencyconverter.repository.converter.ConverterRepositoryImpl
import com.tristanlarsin.currencyconverter.ui.currency.CurrencyViewModel
import com.tristanlarsin.currencyconverter.ui.currency.CurrencyViewModelFactory
import com.tristanlarsin.currencyconverter.ui.currency.converter.ConverterViewModelFactory
import com.tristanlarsin.veox.data.repository.exchangeRate.ExchangeRateRepository
import com.tristanlarsin.veox.data.repository.exchangeRate.ExchangeRateRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class CurrencyApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@CurrencyApplication))

        bind() from singleton { CurrencyDatabase(instance()) }

        bind() from singleton { instance<CurrencyDatabase>().exchangeRateDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ExchangeRateApiService(instance()) }
        bind<ExchangeRateNetworkDataSource>() with singleton {
            ExchangeRateNetworkDataSourceImpl(
                instance()
            )
        }
        bind<ExchangeRateRepository>() with singleton {
            ExchangeRateRepositoryImpl(
                instance(),
                instance(),
                instance()
            )
        }
        bind() from provider { CurrencyViewModelFactory(instance()) }

        bind<ConverterRepository>() with singleton {
            ConverterRepositoryImpl(instance())
        }
        bind() from provider { ConverterViewModelFactory(instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this.applicationContext as Application)
    }
}