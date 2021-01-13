package com.mathewsmobile.spacebook.ui.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mathewsmobile.spacebook.R
import com.mathewsmobile.spacebook.model.Post
import com.mathewsmobile.spacebook.ui.feed.UserFeedViewModel
import kotlinx.android.synthetic.main.fragment_post.*

class PostFragment : Fragment() {
    
    private lateinit var viewModel: PostViewModel

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
        
        viewModel.loadPost(3455) // TODO Get from arguments
        
        viewModel.postData.observe(viewLifecycleOwner, Observer { post -> 
            showPostInfo(post)
            
            
        })
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