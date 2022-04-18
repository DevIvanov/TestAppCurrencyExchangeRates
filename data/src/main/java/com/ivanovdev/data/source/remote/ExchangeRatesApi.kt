package com.ivanovdev.data.source.remote

import com.ivanovdev.data.repository.RemoteConstants.BASE
import com.ivanovdev.data.repository.RemoteConstants.QUERY_ACCESS_KEY
import com.ivanovdev.data.repository.RemoteConstants.GET_LATEST
import com.ivanovdev.data.repository.RemoteConstants.KEY
import com.ivanovdev.data.repository.RemoteConstants.QUERY_BASE
import com.ivanovdev.domain.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesApi {

    @GET(GET_LATEST)
    suspend fun getLatestRates(
        @Query (QUERY_ACCESS_KEY) key: String = KEY,
        @Query (QUERY_BASE) base: String = BASE
    ): Response
}