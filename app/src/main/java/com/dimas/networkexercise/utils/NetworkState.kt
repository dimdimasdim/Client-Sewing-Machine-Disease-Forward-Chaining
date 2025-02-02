package com.dimas.networkexercise.utils

import com.google.gson.Gson
import com.dimas.networkexercise.data.response.BaseError
import retrofit2.Response

sealed class NetworkState<out T> {
    data class Success<out T>(val data: T): NetworkState<T>()
    data class Error(val error: Exception): NetworkState<Nothing>()
}

fun <T : Any>parseError(error: Response<T>?): NetworkState.Error {
    return try {
        NetworkState.Error(Gson().fromJson(error?.errorBody()?.string(), BaseError::class.java))
    } catch (e: Exception) {
       NetworkState.Error(BaseError(error = e.message.orEmpty()))
    }
}