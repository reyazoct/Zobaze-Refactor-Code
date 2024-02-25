package com.zobaze.zobazerefractortask.domain.model.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("data")
    val data: T?
)