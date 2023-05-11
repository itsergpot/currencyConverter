package com.example.currencyconverter

import com.example.currencyconverter.pojo.SymbolsListRawData
import com.google.gson.Gson
import org.junit.Test

class ParserTest {

    private val gson = Gson()

    val testData = """
    {
        "success": true,
        "symbols": {
        "AED": "United Arab Emirates Dirham",
        "AFN": "Afghan Afghani",
        "ALL": "Albanian Lek",
        "AMD": "Armenian Dram"
        }
    }
    """.trimIndent()

    @Test
    fun parseCurrencyData() {
        gson.fromJson(testData, SymbolsListRawData::class.java)
    }
}