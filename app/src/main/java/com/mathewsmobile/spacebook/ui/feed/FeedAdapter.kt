package com.mathewsmobile.spacebook.ui.feed

import android.content.Intent
import android.net.Uri
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mathewsmobile.spacebook.R
import com.mathewsmobile.spacebook.model.FeedItem
import com.mathewsmobile.spacebook.model.Post
import com.mathewsmobile.spacebook.ui.post.PostFragment.Companion.POST_ID
import java.lang.Exception

class FeedAdapter : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private var feedData: MutableList<FeedItem> = mutableListOf()

    fun addFeedData(newFeedData: List<FeedItem>) {
        val oldCount = feedData.size
        feedData.addAll(newFeedData)
        notifyItemRangeInserted(oldCount, newFeedData.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return when (viewType) {
            NEW_POST_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_new_post, parent, false)
                NewPostViewHolder(view)
            }
            NEW_COMMENT_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_new_comment, parent, false)
                NewCommentViewHolder(view)
            }
            GITHUB_PR_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_pull_request, parent, false)
                PullRequestViewHolder(view)
            }
            GITHUB_PUSH_TYPE -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_push, parent, false)
                PushViewHolder(view)
            }
            HIGH_RATING_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_high_rating, parent, false)
                HighRatingViewHolder(view)
            }
            else -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
                BasicViewHolder(view, viewType)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = feedData[position]
        return when (item.type) {
            FeedItem.Companion.Type.NEW_POST -> NEW_POST_TYPE
            FeedItem.Companion.Type.NEW_COMMENT -> NEW_COMMENT_TYPE
            FeedItem.Companion.Type.GITHUB_NEW_PR, FeedItem.Companion.Type.GITHUB_MERGED_PR -> GITHUB_PR_TYPE
            FeedItem.Companion.Type.GITHUB_PUSH -> GITHUB_PUSH_TYPE
            FeedItem.Companion.Type.HIGH_RATING -> HIGH_RATING_TYPE
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
        const val HIGH_RATING_TYPE = 7
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

        private val title: TextView = view.findViewById(R.id.newPostTitle)
        private val date: TextView = view.findViewById(R.id.newPostDate)

        private val listItem: ConstraintLayout = view.findViewById(R.id.newPostItem)

        override fun bind(item: FeedItem) {
            val newPost = item.getPayload() as Post

            title.text = newPost.title
            date.text = newPost.postedAt

            listItem.setOnClickListener { view ->
                Log.d("NewPostViewHolder", "Trying to view the post ${newPost.id}")
                val bundle = bundleOf(POST_ID  to newPost.id)

                view.findNavController().navigate(R.id.actionViewPost, bundle)
            }
        }
    }

    class NewCommentViewHolder(view: View) : ViewHolder(view) {

        private val commentHeader: TextView = view.findViewById(R.id.newCommentTitle)
        private val commentMessage: TextView = view.findViewById(R.id.newCommentMessage)
        private val commentDate: TextView = view.findViewById(R.id.newCommentDate)

        private val listItem: ConstraintLayout = view.findViewById(R.id.newCommentItem)

        override fun bind(item: FeedItem) {
            val comment = item.getPayload() as FeedItem.NewComment

            commentHeader.text = "Commented on the post ${comment.postId}"
            commentMessage.text = comment.message
            commentDate.text = comment.commentedAt

            listItem.setOnClickListener { view ->
                Log.d(
                    "NewCommentViewHolder",
                    "Trying to view the comment on post ${comment.postId}"
                )
                val bundle = bundleOf(POST_ID  to comment.postId)
                view.findNavController().navigate(R.id.actionViewPost, bundle)
            }
        }
    }

    class PullRequestViewHolder(private val view: View) : ViewHolder(view) {

        private val title: TextView = view.findViewById(R.id.pullRequestTitle)
        private val date: TextView = view.findViewById(R.id.pullRequestDate)

        private val listItem: ConstraintLayout = view.findViewById(R.id.pullRequestItem)

        override fun bind(item: FeedItem) {
            val githubPr = item.getPayload() as FeedItem.GithubPR

            if (item.type == FeedItem.Companion.Type.GITHUB_MERGED_PR) {
                title.text = "Merged #${githubPr.pullRequestNumber} into ${githubPr.repository}"
            } else if (item.type == FeedItem.Companion.Type.GITHUB_NEW_PR) {
                title.text =
                    "Opened a new pull request #${githubPr.pullRequestNumber} for ${githubPr.repository}"
            }

            date.text = item.occurredAt

            listItem.setOnClickListener {
                val githubIntent = Intent(Intent.ACTION_VIEW, Uri.parse(githubPr.url))
                try {
                    view.context.startActivity(githubIntent)
                } catch (e: Exception) {
                    Log.e("PushViewHolder", "Unable to go to github", e)
                }
            }
        }
    }

    class PushViewHolder(private val view: View) : ViewHolder(view) {

        private val title: TextView = view.findViewById(R.id.pushTitle)
        private val date: TextView = view.findViewById(R.id.pushDate)

        private val listItem: ConstraintLayout = view.findViewById(R.id.githubPushItem)

        override fun bind(item: FeedItem) {
            val pushes = item.getPayload() as FeedItem.GithubPush

            title.text = "Pushed a commit to ${pushes.repository} on the branch ${pushes.branch}"
            date.text = item.occurredAt

            listItem.setOnClickListener {
                val githubIntent = Intent(Intent.ACTION_VIEW, Uri.parse(pushes.url))
                try {
                    view.context.startActivity(githubIntent)
                } catch (e: Exception) {
                    Log.e("PushViewHolder", "Unable to go to github", e)
                }
            }
        }
    }

    class HighRatingViewHolder(view: View) : ViewHolder(view) {

        private val date: TextView = view.findViewById(R.id.ratingDate)
        private val ratingAmount: RatingBar = view.findViewById(R.id.ratingBar)

        override fun bind(item: FeedItem) {
            val rating = item.getPayload() as FeedItem.HighRating

            date.text = item.occurredAt
            ratingAmount.rating = rating.rating.toFloat()
        }
    }
}