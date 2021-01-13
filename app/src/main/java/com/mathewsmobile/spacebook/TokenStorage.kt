package com.mathewsmobile.spacebook

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenStorage @Inject constructor() {
    
    private var token: String? = null
    
    fun saveToken(newToken: String) {
        // TODO Save securely. For example, encrypt the token and save in SharedPreferences
        this.token = newToken
    }
    
    fun getToken(): String? {
        // TODO Decrypt securely-stored token
        return token
    }
    
    fun tokenValid(): Boolean {
        return token != null && !token.isNullOrBlank()
    }
}