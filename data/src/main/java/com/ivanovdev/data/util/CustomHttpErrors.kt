package com.ivanovdev.data.util

import com.ivanovdev.data.model.pojo.common.error.ErrorResponse
import com.google.gson.Gson
import com.ivanovdev.domain.error.ApplicationError
import retrofit2.HttpException

fun HttpException.getCustomHttpError(): Int? {
    return try {
        this.response()?.errorBody()?.charStream()?.let {
            Gson().fromJson(it, ErrorResponse::class.java).error
        }
    } catch (exception: Exception) {
        null
    }
}

fun Int?.getApplicationErrorOrNull() = when (this) {
    1    -> ApplicationError.EmptyFiled
    else -> null
}