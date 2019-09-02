package com.tristanlarsin.currencyconverter.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "exchange_rates")
data class ExchangeRateEntity (
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Int,
    @SerializedName("success")
    val success: Boolean = false,
    @SerializedName("timestamp")
    val timestamp: String? = null,
    @SerializedName("base")
    val base: String? = null,
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("rates")
    val rates: Map<String, Double>? = null
)