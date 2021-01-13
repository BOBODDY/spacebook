package com.mathewsmobile.spacebook.model

data class Comment(val id: Int, val message: String, val userId: Int, val postId: Int, val commentedAt: String)