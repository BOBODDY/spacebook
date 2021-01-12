package com.mathewsmobile.spacebook.model

data class FeedResponse(val status: String, val data: List<FeedItem>, val pagination: PageData) {
    
    data class PageData(val currentPage: Int, val perPage: Int, val totalPages: Int, val totalEntries: Int)
}