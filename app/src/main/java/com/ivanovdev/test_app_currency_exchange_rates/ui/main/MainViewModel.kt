package com.ivanovdev.test_app_currency_exchange_rates.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel: ViewModel() {

    private val _itemSortDialog = MutableStateFlow(0)
    val itemSortDialog: StateFlow<Int> get() = _itemSortDialog

    fun setItemSortDialog(index: Int) {
        _itemSortDialog.value = index
    }
}