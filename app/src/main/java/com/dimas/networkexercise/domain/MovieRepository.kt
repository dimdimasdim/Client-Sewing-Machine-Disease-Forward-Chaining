package com.dimas.networkexercise.domain

import com.dimas.networkexercise.data.NetworkService
import com.dimas.networkexercise.data.response.BaseError
import com.dimas.networkexercise.data.response.mapToMovie
import com.dimas.networkexercise.domain.model.Movie
import com.dimas.networkexercise.utils.NetworkState
import com.dimas.networkexercise.utils.parseError

class MovieRepository(private val service: NetworkService) {

    suspend fun getMovies(page: Int): NetworkState<List<Movie>> {
        return try {
            val response = service.getNowPlaying(page)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    body.data?.mapToMovie()?.let {
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
}