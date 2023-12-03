package com.ddd.sikdorok.home.month

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ddd.sikdorok.core_ui.R
import com.ddd.sikdorok.home.databinding.ItemMonthBinding
import org.joda.time.DateTime

class HomeMonthViewHolder(
    private val binding: ItemMonthBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Pair<DateTime, String>, listener: ((String) -> Unit)) {
        with(binding) {
            val date = item.first.toString("yyyy-MM-dd")
            month.text = item.first.toString("yyyy년 MM월")

            if(date == item.second) {
                month.setTextAppearance(com.ddd.sikdorok.core_design.R.style.Typography_H3_Bold)
                month.setTextColor(ContextCompat.getColor(root.context, com.ddd.sikdorok.core_design.R.color.text_color))

            } else {
                month.setTextAppearance(com.ddd.sikdorok.core_design.R.style.Typography_Body1_Regular)
                month.setTextColor(ContextCompat.getColor(root.context, com.ddd.sikdorok.core_design.R.color.gray3))
            }

            root.setOnClickListener {
                listener.invoke(date)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): HomeMonthViewHolder {
            val binding = ItemMonthBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return HomeMonthViewHolder(binding)
        }
    }
}
