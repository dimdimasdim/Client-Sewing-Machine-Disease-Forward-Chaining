package com.dimas.networkexercise.utils

sealed class UIState<T: Any>

class Initiate<T: Any>: UIState<T>()
data class Success<T: Any>(val data: T): UIState<T>()
data class Error<T: Any>(val message: String): UIState<T>()
data class Loading<T: Any>(val isLoading: Boolean): UIState<T>()