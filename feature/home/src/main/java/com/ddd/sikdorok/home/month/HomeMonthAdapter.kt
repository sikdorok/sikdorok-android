package com.ddd.sikdorok.home.month

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import org.joda.time.DateTime

class HomeMonthAdapter(
    private val listener: (String) -> Unit
) : ListAdapter<Pair<DateTime, String>, HomeMonthViewHolder>(HomeMonthDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMonthViewHolder {
        return HomeMonthViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HomeMonthViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }
}

private class HomeMonthDiffUtil : DiffUtil.ItemCallback<Pair<DateTime, String>>() {
    override fun areItemsTheSame(
        oldItem: Pair<DateTime, String>,
        newItem: Pair<DateTime, String>
    ): Boolean {
        return oldItem.first == newItem.first
    }

    override fun areContentsTheSame(
        oldItem: Pair<DateTime, String>,
        newItem: Pair<DateTime, String>
    ): Boolean {
        return oldItem == newItem
    }
}