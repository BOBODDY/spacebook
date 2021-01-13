package com.mathewsmobile.spacebook.ui.post

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mathewsmobile.spacebook.FeedRepository
import com.mathewsmobile.spacebook.model.FeedItem
import com.mathewsmobile.spacebook.model.Post
import com.mathewsmobile.spacebook.ui.feed.UserFeedViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ApplicationComponent

class PostViewModel (application: Application) : AndroidViewModel(application) {
    
    @EntryPoint
    @InstallIn(ApplicationComponent::class)
    interface PostViewModelEntryPoint {
        fun feedRepository(): FeedRepository
    }
    
    private val entryPoint: PostViewModelEntryPoint by lazy {
        EntryPointAccessors.fromApplication(
            getApplication(),
            PostViewModelEntryPoint::class.java
        )
    }
    
    private val feedRepository: FeedRepository by lazy {
        entryPoint.feedRepository()
    }
    
    private val postIdLive = MutableLiveData<Int>()
    val postData: LiveData<Post> = Transformations.switchMap(postIdLive) { postId ->
        feedRepository.getPost(postId)
    }
//    val commentsData: LiveData<List<Comment>> TODO
    
    fun loadPost(postId: Int) {
        postIdLive.postValue(postId)
    }
}