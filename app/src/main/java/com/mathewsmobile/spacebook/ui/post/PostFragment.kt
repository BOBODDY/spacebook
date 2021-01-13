package com.mathewsmobile.spacebook.ui.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mathewsmobile.spacebook.R
import com.mathewsmobile.spacebook.model.Post
import com.mathewsmobile.spacebook.ui.feed.UserFeedViewModel
import kotlinx.android.synthetic.main.fragment_post.*

class PostFragment : Fragment() {

    private lateinit var viewModel: PostViewModel

    private val commentsAdapter = CommentsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        
        val postId = 3455  // TODO Get from arguments

        viewModel.loadPost(postId)

        viewModel.postData.observe(viewLifecycleOwner, Observer { post ->
            showPostInfo(post)
            
            postPullToRefresh.isRefreshing = false
        })

        viewModel.commentsData.observe(viewLifecycleOwner, Observer { comments ->
            val numComments = "${comments.size} comments"

            postNumComments.text = numComments

            commentsAdapter.setComments(comments)
            
            postPullToRefresh.isRefreshing = false
        })

        postComments.layoutManager = LinearLayoutManager(requireContext())
        postComments.adapter = commentsAdapter
        
        postPullToRefresh.setOnRefreshListener { 
            viewModel.loadPost(postId)
            postPullToRefresh.isRefreshing = true
        }
    }

    private fun showPostInfo(post: Post) {
        postTitle.text = post.title
        postBody.text = post.body
        postAuthor.text = post.author.name

        post.author.rating?.let {
            postAuthorRating.rating = it.toFloat()
        }
    }
}