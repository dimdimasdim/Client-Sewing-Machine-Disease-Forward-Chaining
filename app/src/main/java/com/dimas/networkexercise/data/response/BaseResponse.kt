package com.dimas.networkexercise.data.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<Response>(
    @SerializedName("results", alternate = ["data"])
    val data: Response?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?
)