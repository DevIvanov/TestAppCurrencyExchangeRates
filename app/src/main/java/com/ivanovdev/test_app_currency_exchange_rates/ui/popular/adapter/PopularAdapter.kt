package com.ivanovdev.test_app_currency_exchange_rates.ui.popular.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ivanovdev.domain.model.Currency
import com.ivanovdev.test_app_currency_exchange_rates.R
import com.ivanovdev.test_app_currency_exchange_rates.databinding.RecyclerViewItemPopularBinding
import java.math.BigDecimal
import java.math.RoundingMode

class PopularAdapter(
    private val listener: OnFavoriteClickListener
) : ListAdapter<Currency, PopularAdapter.ItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemPersonBinding = RecyclerViewItemPopularBinding.inflate(
            layoutInflater, parent, false)
        return ItemViewHolder(itemPersonBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(
        private val binding: RecyclerViewItemPopularBinding
        ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Currency) = with(itemView) {
            binding.tvName.text = item.id
            val value = BigDecimal(item.value).setScale(5, RoundingMode.HALF_EVEN)
            binding.tvValue.text = value.toString()
            binding.imgFavorite.setImageResource(if (item.isFavorite)
                R.drawable.ic_star_choosen
            else
                R.drawable.ic_star)

            binding.imgFavorite.setOnClickListener {
                listener.onFavoriteClick(item)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency):
                Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Currency, newItem: Currency):
                Boolean = oldItem.id == newItem.id
    }

    interface OnFavoriteClickListener {
        fun onFavoriteClick(item: Currency)
    }
}