package com.ivanovdev.test_app_currency_exchange_rates.ui.currency_list

import com.ivanovdev.domain.model.Currency
import com.ivanovdev.domain.usecase.CurrencyDBUseCase
import com.ivanovdev.test_app_currency_exchange_rates.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    private val currencyDBUseCase: CurrencyDBUseCase
): BaseViewModel() {

    val currencyList = currencyDBUseCase.readAll()

    private val _editedList = MutableStateFlow<List<Currency>>(listOf())
    val editedList: StateFlow<List<Currency>> get() = _editedList

    private val _currentList = MutableStateFlow<List<Currency>>(listOf())
    val currentList: StateFlow<List<Currency>> get() = _currentList


    fun setEditedList(list: List<Currency>) {
        _editedList.value = list
    }

    fun setCurrentList(list: List<Currency>) {
        _currentList.value = list
    }
}