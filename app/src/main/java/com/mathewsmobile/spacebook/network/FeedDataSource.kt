package com.mathewsmobile.spacebook.network

import androidx.paging.PageKeyedDataSource
import com.mathewsmobile.spacebook.model.FeedItem

class FeedDataSource: PageKeyedDataSource<Int, FeedItem>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, FeedItem>
    ) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, FeedItem>) {
        TODO("Not yet implemented")
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, FeedItem>) {
        TODO("Not yet implemented")
    }
}