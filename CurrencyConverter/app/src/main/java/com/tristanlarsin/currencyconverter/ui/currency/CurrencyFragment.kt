package com.tristanlarsin.currencyconverter.ui.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tristanlarsin.currencyconverter.data.db.entity.ExchangeRateEntity
import com.tristanlarsin.currencyconverter.ui.adapters.CurrencyListAdapter
import com.tristanlarsin.currencyconverter.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.currency_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import androidx.recyclerview.widget.DividerItemDecoration
import com.tristanlarsin.currencyconverter.ui.currency.converter.ConverterFragment

class CurrencyFragment : ScopedFragment(), KodeinAware,
    CurrencyListAdapter.RecyclerViewClickListener,
    ConverterFragment.ConverterListener {

    override fun recyclerViewListClicked(item: Map<String, Double>) {
        viewModel.setSelectedRate(item)

        val frag = ConverterFragment()
        frag.setTargetFragment(this,0)
        frag.isCancelable = true
        frag.show(fragmentManager, "Dialog")
    }

    override val kodein by closestKodein()

    private val viewModelFactory: CurrencyViewModelFactory by instance()

    private lateinit var viewModel: CurrencyViewModel

    private lateinit var exchangeRateData: LiveData<out ExchangeRateEntity>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.tristanlarsin.currencyconverter.R.layout.currency_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CurrencyViewModel::class.java)

        fetchData()
    }

    private fun fetchData() = launch {
        exchangeRateData = viewModel.exchangeRates.await()
        exchangeRateData.observe(this@CurrencyFragment, Observer {
            if (it == null) return@Observer
            println("Data size: " + it.rates!!.size)
            bindUI()
        })
    }

    private fun bindUI() {
        if(exchangeRateData.value!!.rates!!.isNotEmpty()) {
            val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)


            val adapter = activity?.let { CurrencyListAdapter(it,this, exchangeRateData)}
            lvCurrencies.setHasFixedSize(true)
            lvCurrencies.layoutManager = layoutManager

            val dividerItemDecoration = DividerItemDecoration(lvCurrencies.context, layoutManager.orientation)
            lvCurrencies.addItemDecoration(dividerItemDecoration)
            lvCurrencies.adapter = adapter
        }
    }
}