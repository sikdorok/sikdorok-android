package com.ddd.sikdorok.home.feed

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ddd.sikdorok.shared.home.HomeDailyFeed

class HomeFeedAdapter(
    private val listener: (String) -> Unit
) : ListAdapter<HomeDailyFeed, HomeFeedViewHolder>(HomeFeedListDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFeedViewHolder {
        return HomeFeedViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HomeFeedViewHolder, position: Int) {
        holder.bind(getItem(position), itemCount > 1, listener)
    }
}

private class HomeFeedListDiffUtil : DiffUtil.ItemCallback<HomeDailyFeed>() {
    override fun areItemsTheSame(
        oldItem: HomeDailyFeed,
        newItem: HomeDailyFeed
    ): Boolean {
        return oldItem.feedId == newItem.feedId
    }

    override fun areContentsTheSame(
        oldItem: HomeDailyFeed,
        newItem: HomeDailyFeed
    ): Boolean {
        return oldItem == newItem
    }
}