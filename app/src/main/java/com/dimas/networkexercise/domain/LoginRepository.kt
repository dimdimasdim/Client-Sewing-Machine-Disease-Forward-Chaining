package com.dimas.networkexercise.domain

import com.dimas.networkexercise.data.NetworkService
import com.dimas.networkexercise.data.request.LoginRequest
import com.dimas.networkexercise.data.response.BaseError
import com.dimas.networkexercise.data.response.mapToUser
import com.dimas.networkexercise.domain.model.User
import com.dimas.networkexercise.utils.NetworkState
import com.dimas.networkexercise.utils.parseError

class LoginRepository (private val service: NetworkService) {

    suspend fun postLogin(request: LoginRequest): NetworkState<User> {
        return try {
            val response = service.postLogin(loginRequest = request)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    body.data?.mapToUser()?.let { user ->
                        NetworkState.Success(user)
                    } ?: run { NetworkState.Error(error = BaseError(error = "User Null Response")) }
                } else {
                    parseError(response)
                }
            } else {
                parseError(response)
            }
        } catch (e: Exception) {
            NetworkState.Error(error = e)
        }
    }

}