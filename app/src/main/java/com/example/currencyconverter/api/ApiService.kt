package com.example.currencyconverter.api

import com.example.currencyconverter.pojo.SymbolsListRawData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    // Returns all available currencies
    @GET("symbols")
    fun getCurrencies(
        @Header(HEADER_PARAM_API_KEY) apikey: String = ""
    ): Single<SymbolsListRawData>

    companion object {
        private const val HEADER_PARAM_API_KEY = "apikey"
    }
}