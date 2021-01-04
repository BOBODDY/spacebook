package com.mathewsmobile.spacebook.model

data class LoginResponse(val status: String, val data: LoginData?, val error: LoginError?) {
    
    fun loginSuccessful(): Boolean {
        return data != null && error == null
    }
    
    data class LoginData(val id: Long, val email: String, val name: String, val registeredAt: String, val githubUsername: String?, val rating: Double)
    
    data class LoginError(val type: String)
}