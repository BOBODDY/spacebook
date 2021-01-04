package com.mathewsmobile.spacebook

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mathewsmobile.spacebook.model.LoginRequest
import com.mathewsmobile.spacebook.model.LoginResponse
import com.mathewsmobile.spacebook.network.LoginService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AuthenticationService @Inject constructor(private val loginService: LoginService) {

    private val loginResponseLive = MutableLiveData<LoginResponse>()

    fun authenticateUser(loginRequest: LoginRequest): LiveData<LoginResponse> {
        val response = loginService.login(loginRequest)
        response.enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG, "Exception signing in", t)
                loginResponseLive.postValue(LoginResponse("FAILED", null, null))
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        // TODO Get Authorizaton header (that's where the token lives), save somewhere secure
                        val authHeader = response.headers()["authorization"]
                        
                        loginResponseLive.postValue(it)
                    }
                } else {
                    Log.e(TAG, "Error signing in: ${response.errorBody()?.string()}")

                    loginResponseLive.postValue(LoginResponse("FAILED", null, null))
                }
            }

        })
        return loginResponseLive
    }

    companion object {
        private const val TAG = "AuthenticationService"
    }
}