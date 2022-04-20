package com.ivanovdev.data.constants

import com.ivanovdev.data.BuildConfig

object RemoteConstants {
    const val BASE_URL = "http://api.exchangeratesapi.io/v1/"
    const val GET_LATEST = "latest"
    const val QUERY_ACCESS_KEY = "access_key"
    const val QUERY_BASE = "base"
    const val BASE = "EUR"

    const val KEY = BuildConfig.EXCHANGERATES_ACCESS_KEY
}