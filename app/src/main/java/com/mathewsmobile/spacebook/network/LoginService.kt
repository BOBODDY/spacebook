package com.mathewsmobile.spacebook.network

import com.mathewsmobile.spacebook.model.LoginRequest
import com.mathewsmobile.spacebook.model.LoginResponse
import okhttp3.Request
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("session")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}