package com.mathewsmobile.spacebook.model

data class CommentsResponse(val status: String, val data: List<Comment>, val pagination: PageData) {
}