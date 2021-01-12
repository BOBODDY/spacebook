package com.mathewsmobile.spacebook.ui.feed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mathewsmobile.spacebook.FeedRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ApplicationComponent

class UserFeedViewModel(application: Application) : AndroidViewModel(application) {
    
    @EntryPoint
    @InstallIn(ApplicationComponent::class)
    interface UserFeedViewModelEntryPoint {
        fun feedRepository(): FeedRepository
    }
    
    private val repository: FeedRepository by lazy {
        val entryPoint = EntryPointAccessors.fromApplication(
            getApplication(),
            UserFeedViewModelEntryPoint::class.java
        )
        
        return@lazy entryPoint.feedRepository()
    }
    
    private val currentUserId = MutableLiveData<Int>()
    val userFeed = Transformations.switchMap(currentUserId) { currentUserId ->
        repository.getUserFeed(currentUserId)
    }
    
    fun setUserId(userId: Int) {
        currentUserId.postValue(userId)
    }
}