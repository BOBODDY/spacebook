package com.mathewsmobile.spacebook.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mathewsmobile.spacebook.AuthenticationService
import com.mathewsmobile.spacebook.model.LoginRequest
import com.mathewsmobile.spacebook.model.LoginResponse
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ApplicationComponent

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    @EntryPoint
    @InstallIn(ApplicationComponent::class)
    interface LoginViewModelEntryPoint {
        fun authenticationService(): AuthenticationService
    }

    private val loginStatusLive: MutableLiveData<LoginResponse> = MutableLiveData()
    val loginStatus: LiveData<LoginResponse> = loginStatusLive

    fun login(email: String, password: String): LiveData<LoginResponse> {
        val hiltEntryPoint = EntryPointAccessors.fromApplication(
            getApplication(),
            LoginViewModelEntryPoint::class.java
        )

        val loginResponse =
            hiltEntryPoint.authenticationService().authenticateUser(LoginRequest(email, password))
        
        return loginResponse
    }
}