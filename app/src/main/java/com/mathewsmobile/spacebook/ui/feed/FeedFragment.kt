package com.mathewsmobile.spacebook.ui.feed

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mathewsmobile.spacebook.R
import com.mathewsmobile.spacebook.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : Fragment() {

    lateinit var viewModel: UserFeedViewModel
    
    private val feedAdapter = FeedAdapter()
    private var shouldLoad = true

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
        
        viewModel.userFeed.observe(viewLifecycleOwner, Observer { 
            val data = it.data
            
            Log.d(TAG, "Loading ${data.size} items into the feed")
            
            feedAdapter.addFeedData(data)
            shouldLoad = true
        })
        
        userFeed.adapter = feedAdapter
        userFeed.layoutManager = LinearLayoutManager(requireContext())
        
        userFeed.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                
                val range = recyclerView.computeVerticalScrollRange()
                val currentScroll = recyclerView.computeVerticalScrollOffset()
                
                
                val currentProgress = currentScroll.toDouble() / range.toDouble()

                Log.d(TAG, "Currently scrolled ${currentProgress * 100}%")
                if (currentProgress >= 0.75 && shouldLoad) {
                    viewModel.loadNextPage()
                    shouldLoad = false
                }
            }
        })
    }
    
    companion object {
        private const val TAG = "FeedFragment"
    }
}