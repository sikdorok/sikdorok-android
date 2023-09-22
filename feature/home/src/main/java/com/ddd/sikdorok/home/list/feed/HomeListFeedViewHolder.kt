package com.ddd.sikdorok.home.list.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ddd.sikdorok.core_ui.util.bindVisibleOrGone
import com.ddd.sikdorok.home.databinding.ItemHomeListBinding
import com.ddd.sikdorok.shared.home.DailyFeed

class HomeListFeedViewHolder(
    private val binding: ItemHomeListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DailyFeed, listener: ((String) -> Unit)) {
        with(binding) {

            val adapter = HomeListFeedItemAdapter {
                listener.invoke(it)
            }

            titleDate.text = item.date

            groupMorning.bindVisibleOrGone(item.feeds.morning.isNotEmpty())
            groupLunch.bindVisibleOrGone(item.feeds.afternoon.isNotEmpty())
            groupDinner.bindVisibleOrGone(item.feeds.evening.isNotEmpty())
            groupSnack.bindVisibleOrGone(item.feeds.snack.isNotEmpty())

            listMorning.adapter = adapter
            listLunch.adapter = adapter
            listDinner.adapter = adapter
            listSnack.adapter = adapter
        }
    }

    companion object {
        fun create(parent: ViewGroup): HomeListFeedViewHolder {
            val binding = ItemHomeListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return HomeListFeedViewHolder(binding)
        }
    }
}
