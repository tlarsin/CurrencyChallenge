package com.tristanlarsin.currencyconverter.ui.currency.converter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.tristanlarsin.veox.ui.base.ScopedDialogFragment
import kotlinx.android.synthetic.main.currency_converter_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import android.text.Editable
import com.tristanlarsin.currencyconverter.R


class ConverterFragment : ScopedDialogFragment(), KodeinAware{

    override val kodein by closestKodein()

    private val viewModelFactory: ConverterViewModelFactory by instance()
    private lateinit var viewModel: ConverterViewModel
    private lateinit var callBack: ConverterListener

    private lateinit var selectedRateData: Map<String, Double>

    interface ConverterListener {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.currency_converter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ConverterViewModel::class.java)

        bindUI()
    }

    override fun onResume() {
        super.onResume()

        // Sets width to match parent
        val params = dialog.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = params as android.view.WindowManager.LayoutParams
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Make sure context is an instance of Activity
        if (context is Activity) {
            // Exception handler used to ensure interface was correctly implemented by MainActivity
            try {
                callBack = targetFragment as ConverterListener
            } catch (e: ClassCastException) {
                throw ClassCastException("$context must implement ConverterListener")
            }

        }
    }

    private fun bindUI() {
        selectedRateData = viewModel.getSelectedCurrency()

        tvSelectedRate.text = String.format("%.2f", selectedRateData.values.toList()[0])
        tvSelectedName.text = selectedRateData.keys.toList()[0]

        etBaseRate.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                val newRate = selectedRateData.values.toList()[0] * s.toString().toDouble()
                tvSelectedRate.text = String.format("%.2f",newRate)
            }
        })
    }

}