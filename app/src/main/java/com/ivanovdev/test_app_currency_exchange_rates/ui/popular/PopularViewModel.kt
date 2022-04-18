package com.ivanovdev.test_app_currency_exchange_rates.ui.popular

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ivanovdev.domain.model.Currency
import com.ivanovdev.domain.result.onError
import com.ivanovdev.domain.result.onSuccess
import com.ivanovdev.domain.usecase.RatesUseCase
import com.ivanovdev.test_app_currency_exchange_rates.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val useCase: RatesUseCase
): BaseViewModel() {

    private val _popularList = MutableStateFlow<List<Currency>>(listOf())
    val popularList: StateFlow<List<Currency>> get() = _popularList

    fun getPopularList() {
        viewModelScope.launch {
            useCase.getPopularRates()
                .onSuccess {
                    try {
                        _popularList.emit(it.mapToList())
                    }catch (e: Exception){
                        Log.e(TAG, "exception = $e")
                    }
                }
                .onError { error.emit(it) }
        }
    }

    fun updatePopularList(item: Currency) {
        val newList = _popularList.value
        newList.find { it.name == item.name }?.isFavorite = !item.isFavorite
        Log.e(TAG, "newList = $newList")
        _popularList.value = newList
    }

    fun sortByName(lowToHigh: Boolean) {
        _popularList.value = if (lowToHigh)
            _popularList.value.sortedBy { it.name }
        else
            _popularList.value.sortedBy { it.name }.reversed()
    }

    fun sortByValue(lowToHigh: Boolean) {
        _popularList.value = if (lowToHigh)
            _popularList.value.sortedBy { it.value }
        else
            _popularList.value.sortedBy { it.value }.reversed()
    }

    companion object{
        private val TAG = PopularViewModel::class.java.simpleName
    }
}