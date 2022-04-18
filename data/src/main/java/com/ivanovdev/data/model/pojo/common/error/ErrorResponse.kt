package com.ivanovdev.data.model.pojo.common.error

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error") val error: Int
)