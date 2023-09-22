package com.ddd.sikdorok.home.list.feed

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ddd.sikdorok.shared.home.DailyFeed

class HomeListFeedAdapter(
    private val listener: (String) -> Unit
) : ListAdapter<DailyFeed, HomeListFeedViewHolder>(HomeListFeedDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListFeedViewHolder {
        return HomeListFeedViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HomeListFeedViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }
}

private class HomeListFeedDiffUtil : DiffUtil.ItemCallback<DailyFeed>() {
    override fun areItemsTheSame(
        oldItem: DailyFeed,
        newItem: DailyFeed
    ): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(
        oldItem: DailyFeed,
        newItem: DailyFeed
    ): Boolean {
        return oldItem == newItem
    }
}