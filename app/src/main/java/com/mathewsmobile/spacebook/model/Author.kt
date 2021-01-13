package com.mathewsmobile.spacebook.model

data class Author(
    val id: Int,
    val email: String,
    val name: String,
    val registeredAt: String,
    val githubUsername: String?,
    val rating: Double?
)
