package com.mathewsmobile.spacebook.ui.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mathewsmobile.spacebook.R
import com.mathewsmobile.spacebook.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : Fragment() {

    lateinit var viewModel: UserFeedViewModel
    
    val feedAdapter = FeedAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(UserFeedViewModel::class.java)
        
        viewModel.setUserId(41) // TODO Change this
        
        viewModel.userFeed.observe(viewLifecycleOwner, Observer { 
            val data = it.data
            
            feedAdapter.setFeedData(data)
        })
        
        userFeed.adapter = feedAdapter
        userFeed.layoutManager = LinearLayoutManager(requireContext())
    }
}