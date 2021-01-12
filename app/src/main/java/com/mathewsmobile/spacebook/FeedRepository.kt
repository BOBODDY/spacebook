package com.mathewsmobile.spacebook

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mathewsmobile.spacebook.model.FeedResponse
import com.mathewsmobile.spacebook.network.FeedService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class FeedRepository @Inject constructor(private val feedService: FeedService) {
    
    fun getUserFeed(userId: Int): LiveData<FeedResponse> {
        val data = MutableLiveData<FeedResponse>()
        feedService.getUserFeed(userId).enqueue(object : Callback<FeedResponse> {
            override fun onFailure(call: Call<FeedResponse>, t: Throwable) {
                Log.e(TAG, "Failure in getting user feed for user $userId", t)
            }

            override fun onResponse(call: Call<FeedResponse>, response: Response<FeedResponse>) {
                val body = response.body()
                body?.let {
                    Log.d(TAG, "Got the user's feed: ${it.status}")
                    data.postValue(body)
                } ?: Log.d(TAG, "Something happened with the user's feed")
            }

        })
        
        return data
    }
    
    companion object {
        const val TAG = "FeedRepository"
    }
}