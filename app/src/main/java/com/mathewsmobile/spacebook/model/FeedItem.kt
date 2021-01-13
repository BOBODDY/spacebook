package com.mathewsmobile.spacebook.model

import com.google.gson.Gson

data class FeedItem(val id: Int, val userId: Int, val occurredAt: String, val type: Type, val data: Map<String, Any>) {
    
    fun getPayload(): Payload {
        return when (type) {
            Type.GITHUB_MERGED_PR, Type.GITHUB_NEW_PR -> convertPayloadToClass(data, GithubPR::class.java)
            Type.GITHUB_PUSH -> convertPayloadToClass(data, GithubPush::class.java)
            Type.NEW_COMMENT -> convertPayloadToClass(data, NewComment::class.java)
            Type.NEW_POST -> convertPayloadToClass(data, Post::class.java)
            Type.HIGH_RATING -> convertPayloadToClass(data, HighRating::class.java)
        }
    }
    
    private fun <T> convertPayloadToClass(payload: Map<String, Any>, clazz: Class<T>) : T {
        val json = gson.toJson(payload)
        return gson.fromJson(json, clazz)
    }
    
    interface Payload
    
    data class GithubPR(val githubId: String, val url: String, val repository: String, val pullRequestNumber: Int) : Payload 

    data class GithubPush(val githubId: String, val url: String, val repository: String, val branch: String) : Payload

        data class NewComment(
            val id: Int,
            val message: String,
            val userId: Int,
            val postId: Int,
            val commentedAt: String
        ) : Payload

        data class HighRating(val rating: Double) : Payload

    companion object {
        enum class Type {
            NEW_POST, NEW_COMMENT, GITHUB_NEW_PR, GITHUB_MERGED_PR, GITHUB_PUSH, HIGH_RATING
            // GITHUB_NEW_REPO, // These aren't turning up
        }
        
        val gson = Gson()
    }
}