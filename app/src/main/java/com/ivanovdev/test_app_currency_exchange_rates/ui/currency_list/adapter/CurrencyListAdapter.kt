package com.ivanovdev.test_app_currency_exchange_rates.ui.currency_list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ivanovdev.domain.model.Currency
import com.ivanovdev.test_app_currency_exchange_rates.databinding.RecyclerViewItemCurrencyListBinding

class CurrencyListAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<Currency, CurrencyListAdapter.ItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemPersonBinding = RecyclerViewItemCurrencyListBinding.inflate(
            layoutInflater, parent, false)
        return ItemViewHolder(itemPersonBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(
        private val binding: RecyclerViewItemCurrencyListBinding
        ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Currency) = with(itemView) {
            binding.tvName.text = item.id

            binding.root.setOnClickListener {
                listener.onItemClick(item)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency):
                Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Currency, newItem: Currency):
                Boolean = oldItem.id == newItem.id
    }

    interface OnItemClickListener {
        fun onItemClick(item: Currency)
    }
}