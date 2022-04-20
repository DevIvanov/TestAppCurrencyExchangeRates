package com.ivanovdev.data.repository

import com.ivanovdev.data.source.remote.RemoteDataSource
import com.ivanovdev.domain.model.Response
import com.ivanovdev.domain.repository.RatesRepository
import com.ivanovdev.domain.result.Result
import javax.inject.Inject

class RatesRepositoryImpl @Inject constructor(
    private val dataSource: RemoteDataSource
): RatesRepository {

    override suspend fun getPopularRates(baseCurrency: String): Result<Response> {
        return dataSource.getPopularRates(baseCurrency)
    }
}