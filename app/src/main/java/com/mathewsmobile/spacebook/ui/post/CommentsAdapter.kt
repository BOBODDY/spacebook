package com.mathewsmobile.spacebook.ui.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mathewsmobile.spacebook.R
import com.mathewsmobile.spacebook.model.Comment

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {
    
    private var comments = listOf<Comment>()
    
    fun setComments(newComments: List<Comment>) {
        this.comments = newComments
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        
        return CommentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        
        holder.bind(comment)
    }

    class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        
        private val commentMessage: TextView = view.findViewById(R.id.commentMessage)
        private val author: TextView = view.findViewById(R.id.commentAuthor)
        private val date: TextView = view.findViewById(R.id.commentDate)
        private val authorRating: RatingBar = view.findViewById(R.id.commentAuthorRating)
        private val deleteButton: ImageView = view.findViewById(R.id.commentDelete)
        
        fun bind(comment: Comment) {
            commentMessage.text = comment.message
            author.text = comment.userId.toString() // TODO Look up author name
            date.text = comment.commentedAt // TODO Convert to prettier date
            authorRating.rating = 4.0f // TODO Use the value of the author
            
            deleteButton.setOnClickListener { 
                // TODO Delete this comment
            }
        }
    }
}
