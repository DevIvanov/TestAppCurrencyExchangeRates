package com.ivanovdev.domain.usecase

import com.ivanovdev.domain.model.Response
import com.ivanovdev.domain.repository.RatesRepository
import com.ivanovdev.domain.result.Result
import javax.inject.Inject

class RatesUseCase @Inject constructor(
    private val repository: RatesRepository
) {
    suspend fun getPopularRates(baseCurrency: String): Result<Response> {
        return repository.getPopularRates(baseCurrency)
    }
}