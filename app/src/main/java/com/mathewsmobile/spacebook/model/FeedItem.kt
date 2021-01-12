package com.mathewsmobile.spacebook.model

data class FeedItem(val id: Int, val userId: Int, val occurredAt: String, val type: Type, val data: Post) {
    
    data class Post(val id: Int, val title: String, val body: String, val postedAt: String, val author: PostAuthor) {
        
        data class PostAuthor(val id: Int, val email: String, val name: String, val registeredAt: String, val githubUsername: String?, val rating: Double)
    }
    /*
    {
      "id": 0,
      "userId": 0,
      "occurredAt": "2020-01-01T00:00:00Z",
      "type": "NEW_POST",
      "data": {
        "id": 0,
        "title": "string",
        "body": "string",
        "postedAt": "2020-01-01T00:00:00Z",
        "author": {
          "id": 0,
          "email": "someone@example.com",
          "name": "string",
          "registeredAt": "string",
          "githubUsername": "JakeWharton",
          "rating": 4.5
        }
      }
    }
     */

    companion object {
        enum class Type {
            NEW_POST, NEW_COMMENT, HIGH_RATING,
            GITHUB_NEW_REPO, GITHUB_NEW_PR, GITHUB_MERGED_PR, GITHUB_PUSH
        }
    }
}