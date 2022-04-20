package com.ivanovdev.test_app_currency_exchange_rates.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel: ViewModel() {

    private val _popularSortType = MutableStateFlow(0)
    val popularSortType: StateFlow<Int> get() = _popularSortType

    fun setPopularSortType(index: Int) {
        _popularSortType.value = index
    }

    private val _favoriteSortType = MutableStateFlow(0)
    val favoriteSortType: StateFlow<Int> get() = _favoriteSortType

    fun setFavoriteSortType(index: Int) {
        _favoriteSortType.value = index
    }

    private val _fragmentIndex = MutableStateFlow(0)
    val fragmentIndex: StateFlow<Int> get() = _fragmentIndex

    fun setFragmentIndex(index: Int) {
        _fragmentIndex.value = index
    }
}