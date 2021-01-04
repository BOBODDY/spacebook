package com.mathewsmobile.spacebook.model

class FeedItem {

    companion object {
        enum class Type {
            NEW_POST, NEW_COMMENT, HIGH_RATING,
            GITHUB_NEW_REPO, GITHUB_NEW_PR, GITHUB_MERGED_PR, GITHUB_PUSH
        }
    }
}