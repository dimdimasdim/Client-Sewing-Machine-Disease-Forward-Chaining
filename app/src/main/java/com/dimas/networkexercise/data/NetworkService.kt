package com.dimas.networkexercise.data

import com.dimas.networkexercise.data.request.InferenceRequest
import com.dimas.networkexercise.data.request.LoginRequest
import com.dimas.networkexercise.data.response.BaseResponse
import com.dimas.networkexercise.data.response.InferenceResponse
import com.dimas.networkexercise.data.response.LoginResponse
import com.dimas.networkexercise.data.response.MachineDiseaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    @POST("login")
    suspend fun postLogin(
        @Header("is_mock") isMock: Boolean = true,
        @Header("is_guest") isGuest: Boolean = true,
        @Body loginRequest: LoginRequest
    ): Response<BaseResponse<LoginResponse>>

    @GET("machine-disease")
    suspend fun getDiseases(): Response<BaseResponse<List<MachineDiseaseResponse>>>

    @GET("next-codes/{code}")
    suspend fun getNextCode(@Path("code") code: String): Response<BaseResponse<List<String>>>

    @POST("inference")
    suspend fun inference(@Body request: InferenceRequest): Response<BaseResponse<InferenceResponse>>


}