package com.example.currencyconverter.pojo

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SymbolsListRawData(
    @SerializedName("success")
    @Expose
    val success: Boolean,
    @SerializedName("symbols")
    @Expose
    val symbolsData: JsonObject? = null
)