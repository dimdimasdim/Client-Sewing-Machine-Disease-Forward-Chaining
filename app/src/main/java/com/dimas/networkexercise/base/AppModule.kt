package com.dimas.networkexercise.base

import android.util.Log
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.dimas.networkexercise.base.interceptor.HeaderInterceptor
import com.dimas.networkexercise.data.NetworkService
import com.dimas.networkexercise.domain.LoginRepository
import com.dimas.networkexercise.domain.MovieRepository
import com.dimas.networkexercise.presentation.factory.HomeViewModelFactory
import com.dimas.networkexercise.presentation.factory.LoginViewModelFactory
import com.dimas.networkexercise.utils.ApplicationProviderUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AppModule {

    private fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(15, TimeUnit.SECONDS)
        readTimeout(15, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        addInterceptor(ChuckerInterceptor(ApplicationProviderUtils.get()))
        addInterceptor(HeaderInterceptor())
        addInterceptor(HttpLoggingInterceptor {
            message -> Log.d("Http log", message)
        })
    }.build()

    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .baseUrl("Change to your connection")
            .build()
    }

    private fun provideNetworkService(): NetworkService {
        return provideRetrofit().create(NetworkService::class.java)
    }

    private fun provideLoginRepository(): LoginRepository = LoginRepository(provideNetworkService())

    private fun provideMovieRepository(): MovieRepository = MovieRepository(provideNetworkService())

    val loginViewModelFactory = LoginViewModelFactory(provideLoginRepository())

    val homeViewModelFactory = HomeViewModelFactory(provideMovieRepository())
}