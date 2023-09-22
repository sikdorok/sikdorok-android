package com.ddd.sikdorok.home.date

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ddd.sikdorok.home.R
import com.ddd.sikdorok.home.bindHomeMainMealIcon
import com.ddd.sikdorok.home.databinding.ItemWeekdayBinding
import com.ddd.sikdorok.shared.home.WeeklyFeeds
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class HomeDateViewHolder(
    private val binding: ItemWeekdayBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WeeklyFeeds, listener: ((String) -> Unit)) {
        with(binding) {
            if (item.isSelected) {
                weekdayNumber.setBackgroundResource(R.drawable.bg_home_weekday)
                weekdayNumber.setTextColor(
                    ContextCompat.getColor(
                        root.context,
                        com.ddd.sikdorok.core_design.R.color.white
                    )
                )
            } else {
                weekdayNumber.setBackgroundResource(R.drawable.bg_home_weekday_unselected)
                weekdayNumber.setTextColor(
                    ContextCompat.getColor(
                        root.context,
                        com.ddd.sikdorok.core_design.R.color.text_color
                    )
                )
            }

            weekdayIcon.bindHomeMainMealIcon(item.icon)
            weekdayNumber.text =
                "${DateTime.parse(item.time, DateTimeFormat.forPattern("yyyy-MM-dd")).dayOfMonth}"

            root.setOnClickListener {
                listener.invoke(item.time)
            }
        }

    }

    companion object {
        fun create(parent: ViewGroup): HomeDateViewHolder {
            val binding = ItemWeekdayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return HomeDateViewHolder(binding)
        }
    }
}
