package com.mathewsmobile.spacebook.model

data class Post(val id: Int, val title: String, val body: String, val postedAt: String, val author: Author) :
    FeedItem.Payload