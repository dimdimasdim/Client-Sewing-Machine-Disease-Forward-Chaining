package com.dimas.networkexercise.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dimas.networkexercise.domain.LoginRepository
import com.dimas.networkexercise.presentation.viewmodel.LoginViewModel

class LoginViewModelFactory(
    private val repository: LoginRepository
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            LoginViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }


}