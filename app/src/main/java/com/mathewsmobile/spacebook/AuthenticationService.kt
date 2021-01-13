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
import javax.inject.Singleton

@Singleton
class AuthenticationService @Inject constructor(private val loginService: LoginService, private val tokenStorage: TokenStorage) {
    
    val currentUserId = MutableLiveData<Int>()

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
                    response.body()?.let { resp ->
                        val authHeader = response.headers()["authorization"]
                        
                        authHeader?.let {
                            tokenStorage.saveToken(authHeader)
                        }
                        
                        resp.data?.let { userData -> 
                            currentUserId.postValue(userData.id)
                        }
                        
                        loginResponseLive.postValue(resp)
                    }
                } else {
                    Log.e(TAG, "Error signing in: ${response.errorBody()?.string()}")

                    loginResponseLive.postValue(LoginResponse("FAILED", null, null))
                }
            }

        })
        return loginResponseLive
    }
    
    fun alreadyLoggedIn(): Boolean {
        return tokenStorage.tokenValid()
    }

    companion object {
        private const val TAG = "AuthenticationService"
    }
}