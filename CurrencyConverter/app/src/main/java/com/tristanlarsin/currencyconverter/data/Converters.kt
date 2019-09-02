package com.tristanlarsin.currencyconverter.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun mapFromString(value: String): Map<String, Double>? {
        val mapType = object : TypeToken<Map<String, Double>>() {

        }.type
        return Gson().fromJson<Map<String, Double>>(value, mapType)
    }

    @TypeConverter
    fun fromStringMap(map: Map<String, Double>): String {
        val gson = Gson()
        return gson.toJson(map)
    }
}