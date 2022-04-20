package com.ivanovdev.test_app_currency_exchange_rates.base

import androidx.lifecycle.ViewModel
import com.ivanovdev.domain.error.ApplicationError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class BaseViewModel : ViewModel() {
    val error = MutableSharedFlow<ApplicationError>(replay = 1)
}