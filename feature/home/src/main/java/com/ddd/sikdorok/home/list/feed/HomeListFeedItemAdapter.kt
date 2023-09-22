package com.ddd.sikdorok.home.list.feed

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ddd.sikdorok.shared.home.FeedItem

class HomeListFeedItemAdapter(
    private val listener: (String) -> Unit
) : ListAdapter<FeedItem, HomeListFeedItemViewHolder>(HomeFeedItemListDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListFeedItemViewHolder {
        return HomeListFeedItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HomeListFeedItemViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }
}

private class HomeFeedItemListDiffUtil : DiffUtil.ItemCallback<FeedItem>() {
    override fun areItemsTheSame(
        oldItem: FeedItem,
        newItem: FeedItem
    ): Boolean {
        return oldItem.feedId == newItem.feedId
    }

    override fun areContentsTheSame(
        oldItem: FeedItem,
        newItem: FeedItem
    ): Boolean {
        return oldItem == newItem
    }
}