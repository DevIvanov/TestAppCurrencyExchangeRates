package com.ivanovdev.test_app_currency_exchange_rates.ui.favorite.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ivanovdev.domain.model.Currency
import com.ivanovdev.test_app_currency_exchange_rates.databinding.RecyclerViewItemFavoriteBinding
import java.math.BigDecimal
import java.math.RoundingMode

class FavoriteAdapter(
    private val listener: OnDeleteClickListener
) : ListAdapter<Currency, FavoriteAdapter.ItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemPersonBinding = RecyclerViewItemFavoriteBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(itemPersonBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ItemViewHolder(private val binding: RecyclerViewItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Currency, position: Int) = with(itemView) {
            binding.tvName.text = item.id
            val value = BigDecimal(item.value).setScale(5, RoundingMode.HALF_EVEN)
            binding.tvValue.text = value.toString()

            binding.imgRemove.setOnClickListener {
                listener.onDeleteClick(item, position)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean = oldItem.id == newItem.id
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(item: Currency, position: Int)
    }
}