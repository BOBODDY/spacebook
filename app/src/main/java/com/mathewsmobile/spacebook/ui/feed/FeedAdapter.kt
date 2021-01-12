package com.mathewsmobile.spacebook.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mathewsmobile.spacebook.R
import com.mathewsmobile.spacebook.model.FeedItem

class FeedAdapter : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {
    
    private var feedData: List<FeedItem> = listOf()
    
    fun setFeedData(newFeedData: List<FeedItem>) {
        feedData = newFeedData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        
        return when(viewType) {
            NEW_POST_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_new_post, parent, false)
                NewPostViewHolder(view)
            }
            NEW_COMMENT_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_new_comment, parent, false)
                NewCommentViewHolder(view)
            }
            GITHUB_PR_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pull_request, parent, false)
                PullRequestViewHolder(view)
            }
            GITHUB_PUSH_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_push, parent, false)
                PushViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
                BasicViewHolder(view, viewType)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = feedData[position]
        return when(item.type) {
            FeedItem.Companion.Type.NEW_POST -> NEW_POST_TYPE
            FeedItem.Companion.Type.NEW_COMMENT -> NEW_COMMENT_TYPE
            FeedItem.Companion.Type.GITHUB_NEW_PR, FeedItem.Companion.Type.GITHUB_MERGED_PR -> GITHUB_PR_TYPE
            FeedItem.Companion.Type.GITHUB_PUSH -> GITHUB_PUSH_TYPE
        }
    }

    override fun getItemCount(): Int {
        return feedData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = feedData[position]
        
        holder.bind(item)
    }
    
    companion object {
        const val NEW_POST_TYPE = 3
        const val NEW_COMMENT_TYPE = 4
        const val GITHUB_PR_TYPE = 5
        const val GITHUB_PUSH_TYPE = 6
    }

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: FeedItem)
    }
    
    class BasicViewHolder(val view: View, val viewType: Int) : ViewHolder(view) {
        override fun bind(item: FeedItem) {
            view.findViewById<TextView>(R.id.itemTitle).text = "Unknown view type $viewType"
        }
    }
    
    class NewPostViewHolder(view: View) : ViewHolder(view) {
        
        val title: TextView = view.findViewById(R.id.newPostTitle)
        val date: TextView = view.findViewById(R.id.newPostDate)
        
        override fun bind(item: FeedItem) {
            val newPost = item.getPayload() as FeedItem.Post
            
            title.text = newPost.title
            date.text = newPost.postedAt
        }
    }
    
    class NewCommentViewHolder(view: View) : ViewHolder(view) {
        
        val commentHeader: TextView = view.findViewById(R.id.newCommentTitle)
        
        override fun bind(item: FeedItem) {
            val comment = item.getPayload() as FeedItem.NewComment
            
            commentHeader.text = "Commented on a post by user ${comment.userId}"
        }
    }
    
    class PullRequestViewHolder(view: View) : ViewHolder(view) {
        
        private val title: TextView = view.findViewById(R.id.pullRequestTitle)
        
        override fun bind(item: FeedItem) {
            val githubPr = item.getPayload() as FeedItem.GithubPR
            
            if (item.type == FeedItem.Companion.Type.GITHUB_MERGED_PR) {
                title.text = "Merged #${githubPr.pullRequestNumber} into ${githubPr.repository}"
            } else if (item.type == FeedItem.Companion.Type.GITHUB_NEW_PR) {
                title.text = "Opened a new pull request #${githubPr.pullRequestNumber} for ${githubPr.repository}"
            }
        }
    }
    
    class PushViewHolder(view: View) : ViewHolder(view) {
        
        private val title: TextView = view.findViewById(R.id.pushTitle)
        
        override fun bind(item: FeedItem) {
            val pushes = item.getPayload() as FeedItem.GithubPush
            
            title.text = "Pushed commits to ${pushes.repository} on the branch ${pushes.branch}"
        }
    }
}