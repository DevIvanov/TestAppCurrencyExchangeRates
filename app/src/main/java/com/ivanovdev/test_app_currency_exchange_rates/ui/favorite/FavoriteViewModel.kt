package com.ivanovdev.test_app_currency_exchange_rates.ui.favorite

import androidx.lifecycle.viewModelScope
import com.ivanovdev.domain.model.Currency
import com.ivanovdev.domain.usecase.CurrencyDBUseCase
import com.ivanovdev.test_app_currency_exchange_rates.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val currencyDBUseCase: CurrencyDBUseCase
): BaseViewModel() {

    val currencyList = currencyDBUseCase.readAll()

    private val _favoriteList = MutableStateFlow<List<Currency>>(listOf())
    val favoriteList: StateFlow<List<Currency>> get() = _favoriteList

    fun setFavoriteList(list: List<Currency>, index: Int) {
        _favoriteList.value = sortListFirstTime(list, index)
    }

    fun deleteItem(item: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            val newItem = Currency(
                id = item.id,
                value = item.value,
                isFavorite = !item.isFavorite
            )
            currencyDBUseCase.update(newItem)
        }
    }

    fun sortList(index: Int){
        _favoriteList.value = when (index) {
            0 -> _favoriteList.value.sortedBy { it.id }
            1 -> _favoriteList.value.sortedBy { it.id }.reversed()
            2 -> _favoriteList.value.sortedBy { it.value }
            3 -> _favoriteList.value.sortedBy { it.value }.reversed()
            else -> {_favoriteList.value}
        }
    }

    private fun sortListFirstTime (list: List<Currency>, index: Int): List<Currency> {
        return when (index) {
            0 -> list.sortedBy { it.id }
            1 -> list.sortedBy { it.id }.reversed()
            2 -> list.sortedBy { it.value }
            3 -> list.sortedBy { it.value }.reversed()
            else -> {list}
        }
    }
}