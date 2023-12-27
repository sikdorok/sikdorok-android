package com.ddd.sikdorok.home.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ddd.sikdorok.core_ui.util.bindImageRes
import com.ddd.sikdorok.core_ui.util.bindMealIcon
import com.ddd.sikdorok.core_ui.util.bindVisibleOrGone
import com.ddd.sikdorok.home.R
import com.ddd.sikdorok.home.databinding.ItemMealboxBinding
import com.ddd.sikdorok.shared.home.HomeDailyFeed

class HomeFeedViewHolder(
    private val binding: ItemMealboxBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HomeDailyFeed, isNeedRotate: Boolean, listener: ((String) -> Unit)) {
        with(binding) {
            if (isNeedRotate) {
                when {
                    adapterPosition % 2 == 0 -> {
                        containerMealboxImage.rotation = -1.5f
                    }
                    adapterPosition % 2 == 1 -> {
                        containerMealboxImage.rotation = 1.5f
                    }
                }
            }

            // feed id 없을 경우 (초기화 상태)
            if (item.feedId.isEmpty()) {
                Glide.with(binding.root)
                    .load(com.ddd.sikdorok.core_design.R.drawable.ic_nothing_unselected)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(iconTodayMenu)
            } else {
                iconTodayMenu.bindMealIcon(item.icon)
            }

            time.text = item.time
            imgMealbox.bindImageRes(item.photosInfoList?.firstOrNull()?.uploadFullPath)
            containerMealbox.bindVisibleOrGone(!item.photosInfoList.isNullOrEmpty())
            iconIsMain.bindVisibleOrGone(item.isMain)

            if (item.memo.isNullOrEmpty()) {
                memo.setTextColor(ContextCompat.getColor(root.context, R.color.memo_disable))
                memo.text = root.context.getString(R.string.title_today_mealbox_no_memo)
            } else {
                memo.setTextColor(
                    ContextCompat.getColor(
                        root.context,
                        com.ddd.sikdorok.core_design.R.color.text_color
                    )
                )
                memo.text = item.memo
            }

            root.setOnClickListener {
                listener.invoke(item.feedId)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): HomeFeedViewHolder {
            val binding = ItemMealboxBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return HomeFeedViewHolder(binding)
        }
    }
}
