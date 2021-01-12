package com.mathewsmobile.spacebook.di

import com.mathewsmobile.spacebook.FeedRepository
import com.mathewsmobile.spacebook.network.FeedService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {
    
    @Provides
    fun provideFeedRepository(feedService: FeedService): FeedRepository {
        return FeedRepository(feedService)
    }
}