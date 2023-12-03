package com.ddd.sikdorok.home.date

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ddd.sikdorok.shared.home.WeeklyFeeds

class HomeDateAdapter(
    private val listener: (String) -> Unit
) : ListAdapter<WeeklyFeeds, HomeDateViewHolder>(HomeDateListDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeDateViewHolder {
        return HomeDateViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HomeDateViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }
}

private class HomeDateListDiffUtil : DiffUtil.ItemCallback<WeeklyFeeds>() {
    override fun areItemsTheSame(
        oldItem: WeeklyFeeds,
        newItem: WeeklyFeeds
    ): Boolean {
        return oldItem.time == newItem.time
    }

    override fun areContentsTheSame(
        oldItem: WeeklyFeeds,
        newItem: WeeklyFeeds
    ): Boolean {
        return oldItem == newItem
    }
}