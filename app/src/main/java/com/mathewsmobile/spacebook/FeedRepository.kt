package com.mathewsmobile.spacebook

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mathewsmobile.spacebook.model.FeedResponse
import com.mathewsmobile.spacebook.network.FeedService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class FeedRepository @Inject constructor(private val feedService: FeedService) {

    private var currentPage = 1
    private var totalPages = Int.MAX_VALUE
    private val pageLive = MutableLiveData(1)

    fun getUserFeed(userId: Int): LiveData<FeedResponse> {
        val x = Transformations.switchMap(pageLive) { currentPage ->
            val data = MutableLiveData<FeedResponse>()
            feedService.getUserFeed(userId, page = currentPage)
                .enqueue(object : Callback<FeedResponse> {
                    override fun onFailure(call: Call<FeedResponse>, t: Throwable) {
                        Log.e(TAG, "Failure in getting user feed for user $userId", t)
                    }

                    override fun onResponse(
                        call: Call<FeedResponse>,
                        response: Response<FeedResponse>
                    ) {
                        val body = response.body()
                        body?.let {
                            Log.d(TAG, "Got the user's feed: ${it.status}")

                            totalPages = body.pagination.totalPages
                            data.postValue(body)
                        } ?: Log.d(TAG, "Something happened with the user's feed")
                    }

                })

            data
        }

        return x
    }

    fun nextPage() {
        currentPage += 1
        if (currentPage <= totalPages) {
            pageLive.postValue(currentPage)
        }
    }

    companion object {
        const val TAG = "FeedRepository"
    }
}