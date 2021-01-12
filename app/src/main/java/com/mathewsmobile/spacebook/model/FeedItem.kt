package com.mathewsmobile.spacebook.model

import android.util.Log

data class FeedItem(val id: Int, val userId: Int, val occurredAt: String, val type: Type, val data: Map<String, Any>) {
    
    fun getPayload(): Payload {
        return when (type) {
            Type.GITHUB_MERGED_PR, Type.GITHUB_NEW_PR -> GithubPR.fromMap(data)
            Type.GITHUB_PUSH -> GithubPush.fromMap(data)
            Type.NEW_COMMENT -> NewComment.fromMap(data)
            Type.NEW_POST -> Post.fromMap(data)
        }
    }
    
    interface Payload
    
    data class GithubPR(val githubId: String, val url: String, val repository: String, val pullRequestNumber: Int) : Payload {
        companion object {
            fun fromMap(map: Map<String, Any>): GithubPR {
                return GithubPR(map["githubId"] as String, map["url"] as String, map["repository"] as String, (map["pullRequestNumber"] as Double).toInt())
            }
        }
    }

    data class GithubPush(val githubId: String, val url: String, val repository: String, val branch: String) : Payload {
        companion object {
            fun fromMap(map: Map<String, Any>): GithubPush {
                return GithubPush(map["githubId"] as String, map["url"] as String, map["repository"] as String, map["branch"] as String)
            }
        }
    }
    
    data class Post(val id: Int, val title: String, val body: String, val postedAt: String, val author: PostAuthor) : Payload {
        
        data class PostAuthor(val id: Int, val email: String, val name: String, val registeredAt: String, val githubUsername: String?, val rating: Double) {
            companion object {
                fun fromMap(map: Map<String, Any>): PostAuthor {
                    return PostAuthor(map["id"] as Int, map["email"] as String, map["name"] as String, map["registeredAt"] as String, map["githubUsername"] as? String, map["rating"] as Double)
                }
            }
        }
        
        companion object {
            fun fromMap(map: Map<String, Any>): Post {
                return Post(map["id"] as Int, map["title"] as String, map["body"] as String, map["postedAt"] as String, PostAuthor.fromMap(map["author"] as Map<String, Any>))
            }
        }
    }
    
    data class NewComment(val id: Int, val message: String, val userId: Int, val postId: Int, val commentedAt: String) : Payload {
        companion object {
            fun fromMap(map: Map<String, Any>): NewComment {
                Log.d("NewComment", map.toString())
                return NewComment((map["id"] as Double).toInt(), map["message"] as String, (map["userId"] as Double).toInt(), (map["postId"] as Double).toInt(), map["commentedAt"] as String)
            }
        }
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
            NEW_POST, NEW_COMMENT, GITHUB_NEW_PR, GITHUB_MERGED_PR, GITHUB_PUSH
            // HIGH_RATING, GITHUB_NEW_REPO, // These aren't turning up
        }
    }
}