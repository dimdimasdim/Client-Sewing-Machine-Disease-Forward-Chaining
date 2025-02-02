package com.dimas.networkexercise.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimas.networkexercise.data.request.LoginRequest
import com.dimas.networkexercise.data.response.BaseError
import com.dimas.networkexercise.domain.LoginRepository
import com.dimas.networkexercise.domain.model.User
import com.dimas.networkexercise.utils.Error
import com.dimas.networkexercise.utils.Initiate
import com.dimas.networkexercise.utils.Loading
import com.dimas.networkexercise.utils.NetworkState
import com.dimas.networkexercise.utils.Success
import com.dimas.networkexercise.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: LoginRepository
): ViewModel() {

    private val _login = MutableStateFlow<UIState<User>>(Initiate())
    val login: StateFlow<UIState<User>> = _login

    fun postLogin(username: String, password: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _login.value = Loading(true)
            val process = async(Dispatchers.IO) {
                repository.postLogin(LoginRequest(username, password))
            }
            when(val state = process.await()) {
                is NetworkState.Success -> {
                    _login.value = Loading(false)
                    _login.value = Success(data = state.data)
                }
                is NetworkState.Error ->{
                    _login.value = Loading(false)
                    _login.value =
                        Error((state.error as BaseError).error)
                }
            }
        }
    }

}