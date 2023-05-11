package com.example.currencyconverter.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.api.ApiFactory
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

    fun loadData() {
        ApiFactory.apiService.getCurrencies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                currencyData.onNext(it.symbolsData?.entries?.map { entry -> CurrencyItem(entry.key, entry.value) })
                Log.d(TAG, "Success $it")
            }, {
                Log.d(TAG, "Sth went wrong: ${it.message.toString()}, ${it.printStackTrace()}")
            })
    }
}