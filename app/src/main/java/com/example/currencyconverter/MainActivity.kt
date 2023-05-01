package com.example.currencyconverter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.adapters.CurrencyItemAdapter
import com.example.currencyconverter.repo.CurrencyItem
import com.example.currencyconverter.viewmodels.MainActivityViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private val currencyAdapter = CurrencyItemAdapter(mutableListOf())
    private lateinit var mainActivityVM: MainActivityViewModel
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityVM = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        setContentView(R.layout.activity_main)
        val recycler = findViewById<RecyclerView>(R.id.currency_recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = currencyAdapter
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable.add(
            mainActivityVM.getCurrencyPublisher().subscribe {
                refreshData(it)
            }
        )
        mainActivityVM.loadData()
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.dispose()
    }

    private fun refreshData(currencyItems: List<CurrencyItem>) {
        currencyAdapter.currencyItemList.addAll(currencyItems)
        currencyAdapter.notifyDataSetChanged()
    }
}