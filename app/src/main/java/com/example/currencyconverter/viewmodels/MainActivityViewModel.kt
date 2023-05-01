package com.example.currencyconverter.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.api.ApiFactory
import com.example.currencyconverter.pojo.SymbolsListRawData
import com.example.currencyconverter.repo.CurrencyItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class MainActivityViewModel : ViewModel() {

    private val TAG = "MainActivity"
    private val currencyData = PublishSubject.create<List<CurrencyItem>>()

    fun getCurrencyPublisher(): PublishSubject<List<CurrencyItem>> {
        return currencyData
    }

    private fun getCurrencyItemListFromRawData(symbolsListRawData: SymbolsListRawData): List<CurrencyItem> {
        val result = ArrayList<CurrencyItem>()
        val jsonObject = symbolsListRawData.symbolsData ?: return result
        val currencyEntrySet = jsonObject.entrySet()
        for (currencyEntry in currencyEntrySet) {
            result.add(CurrencyItem(currencyEntry.key, currencyEntry.value.asString))
        }
        return result
    }

    fun loadData() {
        ApiFactory.apiService.getCurrencies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { getCurrencyItemListFromRawData(it) }
            .subscribe({
                currencyData.onNext(it)
                Log.d(TAG, "Success $it")
            }, {
                Log.d(TAG, "Sth went wrong: ${it.message.toString()}, ${it.printStackTrace()}")
            })
    }
}