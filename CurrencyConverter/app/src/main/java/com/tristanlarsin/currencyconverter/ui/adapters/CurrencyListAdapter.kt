package com.tristanlarsin.currencyconverter.ui.adapters

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.tristanlarsin.currencyconverter.R
import com.tristanlarsin.currencyconverter.data.db.entity.ExchangeRateEntity
import kotlinx.android.synthetic.main.currency_listitem.view.*
import java.util.*

class CurrencyListAdapter(
    private val activity: Activity,
    private val itemListener: RecyclerViewClickListener,
    private var data: LiveData<out ExchangeRateEntity>
) : RecyclerView.Adapter<CurrencyListAdapter.ViewHolder>() {

    interface RecyclerViewClickListener {
        fun recyclerViewListClicked(item: Map<String, Double>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.currency_listitem, parent, false)

        return ViewHolder(v, parent)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val keys = data.value!!.rates!!.keys.toList()
        val values = data.value!!.rates!!.values.toList()

        holder.name.text = keys[position]
        holder.rate.text = String.format("%.2f", values[position])
    }

    override fun getItemCount(): Int { return data.value!!.rates!!.size }

    inner class ViewHolder @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    internal constructor(itemView: View, private val parent: ViewGroup) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

//        val logo: ImageView
        val name: TextView
        val rate: TextView

        init {
            itemView.setOnClickListener(this)

//            logo = itemView.ivLogo
            name = itemView.tvName
            rate = itemView.tvRate

        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        override fun onClick(v: View) {
            val keys = data.value!!.rates!!.keys.toList()
            val values = data.value!!.rates!!.values.toList()

            itemListener.recyclerViewListClicked(mapOf(Pair(keys[adapterPosition], values[adapterPosition])))
        }

    }
}