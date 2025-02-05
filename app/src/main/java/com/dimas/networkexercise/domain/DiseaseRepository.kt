package com.dimas.networkexercise.domain

import com.dimas.networkexercise.data.NetworkService
import com.dimas.networkexercise.data.response.BaseError
import com.dimas.networkexercise.data.response.mapToDisease
import com.dimas.networkexercise.domain.model.MachineDisease
import com.dimas.networkexercise.utils.NetworkState
import com.dimas.networkexercise.utils.parseError

class DiseaseRepository(private val service: NetworkService) {

    suspend fun getDiseases(): NetworkState<List<MachineDisease>> {
        return try {
            val response = service.getDiseases()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    body.data?.mapToDisease()?.let {
                        NetworkState.Success(it)
                    } ?:
                    run {
                        NetworkState
                            .Error(error = BaseError(error = "Null Response"))
                    }
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

    suspend fun getNextCode(code: String): NetworkState<String> {
        return try {
            val response = service.getNextCode(code)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    NetworkState.Success(body.data?.firstOrNull().orEmpty())
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