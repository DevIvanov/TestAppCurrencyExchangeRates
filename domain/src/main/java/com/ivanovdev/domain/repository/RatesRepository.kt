package com.ivanovdev.domain.repository

import com.ivanovdev.domain.model.Response
import com.ivanovdev.domain.result.Result

interface RatesRepository {
    suspend fun getPopularRates(baseCurrency: String): Result<Response>
}