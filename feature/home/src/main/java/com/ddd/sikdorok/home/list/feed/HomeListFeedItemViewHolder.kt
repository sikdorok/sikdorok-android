package com.ddd.sikdorok.home.list.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ddd.sikdorok.core_ui.util.bindImageRes
import com.ddd.sikdorok.core_ui.util.bindMealIcon
import com.ddd.sikdorok.core_ui.util.bindVisibleOrGone
import com.ddd.sikdorok.home.databinding.ItemHomeListItemBinding
import com.ddd.sikdorok.shared.home.FeedItem

class HomeListFeedItemViewHolder(
    private val binding: ItemHomeListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FeedItem, listener: ((String) -> Unit)) {
        with(binding) {
            if (item.photosInfoList?.isNotEmpty() == true) {
                item.photosInfoList?.firstOrNull()?.let {
                    icon.bindImageRes(it.uploadFullPath)
                }
            }

            content.text = item.memo
            iconMealType.bindMealIcon(item.icon)
            iconIsMain.bindVisibleOrGone(item.isMain)
            time.text = item.time

            root.setOnClickListener {
                listener.invoke(item.feedId)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): HomeListFeedItemViewHolder {
            val binding = ItemHomeListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return HomeListFeedItemViewHolder(binding)
        }
    }
}
