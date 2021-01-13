package com.mathewsmobile.spacebook.ui.feed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mathewsmobile.spacebook.AuthenticationService
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
        fun authenticationService(): AuthenticationService
    }
    
    private val entryPoint: UserFeedViewModelEntryPoint by lazy {
        EntryPointAccessors.fromApplication(
            getApplication(),
            UserFeedViewModelEntryPoint::class.java
        )
    }
    
    private val repository: FeedRepository by lazy {
        return@lazy entryPoint.feedRepository()
    }
    
    private val authService: AuthenticationService by lazy {
        return@lazy entryPoint.authenticationService()
    }
    
    val userFeed = Transformations.switchMap(authService.currentUserId) { currentUserId ->
        repository.getUserFeed(currentUserId)
    }
    
    fun loadNextPage() {
        repository.nextPage()
    }
}