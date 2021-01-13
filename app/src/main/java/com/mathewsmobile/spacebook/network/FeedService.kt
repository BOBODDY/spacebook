package com.mathewsmobile.spacebook.network

import com.mathewsmobile.spacebook.model.FeedResponse
import com.mathewsmobile.spacebook.model.CommentsResponse
import com.mathewsmobile.spacebook.model.PostResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FeedService {
    
    @GET("users/{userId}/feed")
    fun getUserFeed(@Path("userId") userId: Int, 
                    @Query("page") page: Int = 1, 
                    @Query("perPage") perPage: Int = 50): Call<FeedResponse>
    
    @GET("posts/{postId}")
    fun getPost(@Path("postId") postId: Int): Call<PostResponse>

    @GET("posts/{postId}/comments")
    fun getPostComments(@Path("postId") postId: Int): Call<CommentsResponse>
}