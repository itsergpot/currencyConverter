package com.example.currencyconverter

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import com.example.currencyconverter.adapters.CurrencyItemAdapter
import com.example.currencyconverter.api.ApiFactory
import com.example.currencyconverter.pojo.SymbolsListRawData
import com.example.currencyconverter.repo.CurrencyItem
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val currencyAdapter = CurrencyItemAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recycler = findViewById<RecyclerView>(R.id.currency_recycler)
        loadData()
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = currencyAdapter
    }

    private fun getCurrencyItemListFromRawData(symbolsListRawData: SymbolsListRawData): List<CurrencyItem> {
        val result = ArrayList<CurrencyItem>()
        val jsonObject = symbolsListRawData.symbolsData ?: return result
        val currencyEntrySet = jsonObject.entrySet()
        for(currencyEntry in currencyEntrySet) {
            result.add(CurrencyItem(currencyEntry.key, currencyEntry.value.asString))
        }
        return result
    }

    private fun loadData() {
        ApiFactory.apiService.getCurrencies("OYIEE0bB6PzjUmOBqDOfNm6E7ybyP8eZ")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { getCurrencyItemListFromRawData(it) }
            .subscribe({
                currencyAdapter.currencyItemList.addAll(it)
                currencyAdapter.notifyDataSetChanged()
                Log.d(TAG, "Success $it")
            }, {
                Log.d(TAG, "Sth went wrong: ${it.message.toString()}, ${it.printStackTrace()}")
            })
    }
}