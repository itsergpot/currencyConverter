package com.example.currencyconverter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.R
import com.example.currencyconverter.repo.CurrencyItem

class CurrencyItemAdapter(private val currencyItemList: List<CurrencyItem>): RecyclerView.Adapter<CurrencyItemAdapter.CurrencyItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_currency_item, parent, false)
        return CurrencyItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CurrencyItemViewHolder, position: Int) {
        holder.currencyCode.text = currencyItemList[position].currencyCode
        holder.nameOfCurrency.text = currencyItemList[position].nameOfCurrency
    }

    override fun getItemCount() = currencyItemList.size

    inner class CurrencyItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val currencyCode = itemView.findViewById<TextView>(R.id.currency_code)
        val nameOfCurrency = itemView.findViewById<TextView>(R.id.name_of_currency)
    }
}