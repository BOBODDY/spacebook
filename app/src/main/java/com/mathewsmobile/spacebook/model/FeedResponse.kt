package com.mathewsmobile.spacebook.model

data class FeedResponse(val status: String, val data: List<FeedItem>, val pagination: PageData)