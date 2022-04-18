package com.ivanovdev.data.source.remote

import com.ivanovdev.data.util.safeApiCall
import com.ivanovdev.domain.model.Response
import com.ivanovdev.domain.result.Result
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: ExchangeRatesApi
) {

    suspend fun getPopularRates(): Result<Response> {
        return safeApiCall { api.getLatestRates() }
    }

    companion object{
        private val TAG = RemoteDataSource::class.java.simpleName
    }
}