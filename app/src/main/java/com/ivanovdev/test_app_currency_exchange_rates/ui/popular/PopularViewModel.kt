package com.ivanovdev.test_app_currency_exchange_rates.ui.popular

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ivanovdev.domain.model.Currency
import com.ivanovdev.domain.model.UpdateCurrency
import com.ivanovdev.domain.result.onError
import com.ivanovdev.domain.result.onSuccess
import com.ivanovdev.domain.usecase.CurrencyDBUseCase
import com.ivanovdev.domain.usecase.RatesUseCase
import com.ivanovdev.test_app_currency_exchange_rates.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val ratesUseCase: RatesUseCase,
    private val currencyDBUseCase: CurrencyDBUseCase
): BaseViewModel() {

    val listFromDB = currencyDBUseCase.readAll()

    private val _popularList = MutableStateFlow<List<Currency>>(listOf())
    val popularList: StateFlow<List<Currency>> get() = _popularList

    private val _isEmptyDB = MutableStateFlow<Boolean>(false)


    fun setPopularList(list: List<Currency>, index: Int) {
        _popularList.value = sortListFirstTime(list, index)
    }

    fun setIsEmptyDB(isEmpty: Boolean) {
        _isEmptyDB.value = isEmpty
    }

    // Work only when baseCurrency = "EUR", because API is free
    fun getListFromApi(baseCurrency: String) {
        viewModelScope.launch(Dispatchers.IO)  {
            ratesUseCase.getPopularRates(baseCurrency)
                .onSuccess { list ->
                    Log.i(TAG, "Response = $list")
                    try {
                        if (_isEmptyDB.value){
                            insertAll(list.mapToList())
                        }else{
                            updateValuesInDB(list.mapToUpdateList())
                        }
                    }catch (e: Exception){
                        Log.e(TAG, "exception = $e")
                    }
                }
                .onError { error.emit(it) }
        }
    }

    fun updateItemFromDB(item: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            val newItem = Currency(
                id = item.id,
                value = item.value,
                isFavorite = !item.isFavorite
            )
            currencyDBUseCase.update(newItem)
        }
    }

    private fun insertAll(list: List<Currency>) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyDBUseCase.insertAll(list)
        }
    }

    private fun updateValuesInDB(list: List<UpdateCurrency>) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyDBUseCase.updateValues(list)
        }
    }

    fun sortList(index: Int){
        _popularList.value = when (index) {
            0 -> _popularList.value.sortedBy { it.id }
            1 -> _popularList.value.sortedBy { it.id }.reversed()
            2 -> _popularList.value.sortedBy { it.value }
            3 -> _popularList.value.sortedBy { it.value }.reversed()
            else -> {_popularList.value}
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

    companion object{
        private val TAG = PopularViewModel::class.java.simpleName
    }
}