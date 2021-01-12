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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
        
        return BasicViewHolder(view)
    }

    override fun getItemCount(): Int {
        return feedData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = feedData[position]
        
        holder.bind(item)
    }

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: FeedItem)
    }
    
    class BasicViewHolder(val view: View) : ViewHolder(view) {
        override fun bind(item: FeedItem) {
            view.findViewById<TextView>(R.id.itemTitle).text = item.data.title
        }

    }
}