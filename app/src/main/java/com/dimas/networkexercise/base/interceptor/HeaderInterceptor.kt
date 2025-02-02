package com.dimas.networkexercise.base.interceptor

import androidx.datastore.core.DataStore
import com.dimas.networkexercise.domain.model.User
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class HeaderInterceptor(): Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = mapHeaders(chain)
        return chain.proceed(request)
    }

    private fun mapHeaders(chain: Interceptor.Chain): Request {
        val original = chain.request()

       /**
        TODO : you can add header here
        original.headers["Key"] = value
        **/

        return original.newBuilder().build()
    }
}