package com.ddd.sikdorok.home

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.ddd.sikdorok.shared.code.Icon
import com.ddd.sikdorok.shared.code.Tag


@BindingAdapter("bind:home_main_tag_icon")
fun AppCompatImageView.bindHomeMainTag(nowTag : String) {
    when(Tag.values().map { it.code }.indexOf(nowTag)) {
        0 -> setImageDrawable(ContextCompat.getDrawable(context, com.ddd.sikdorok.core_design.R.drawable.ic_sun))
        1 -> setImageDrawable(ContextCompat.getDrawable(context, com.ddd.sikdorok.core_design.R.drawable.ic_sun_cloud_selected))
        2 -> setImageDrawable(ContextCompat.getDrawable(context, com.ddd.sikdorok.core_design.R.drawable.ic_moon))
        3 -> setImageDrawable(ContextCompat.getDrawable(context, com.ddd.sikdorok.core_design.R.drawable.ic_cake))
    }
}

@BindingAdapter("bind:home_main_tag_text")
fun AppCompatTextView.bindHomeMainTag(nowTag : String) {
    text = when(Tag.values().map { it.code }.indexOf(nowTag)) {
        0 -> "아침"
        1 -> "점심"
        2 -> "저녁"
        3 -> "간식"
        else -> "아침"
    }
}

@BindingAdapter("bind:home_main_meal_icon")
fun AppCompatImageView.bindHomeMainMealIcon(nowTag : String) {
    when(Icon.values().map { it.code }.indexOf(nowTag)) {
        0 -> setImageDrawable(ContextCompat.getDrawable(context, com.ddd.sikdorok.core_design.R.drawable.ic_nothing))
        1 -> setImageDrawable(ContextCompat.getDrawable(context, com.ddd.sikdorok.core_design.R.drawable.ic_rice))
        2 -> setImageDrawable(ContextCompat.getDrawable(context, com.ddd.sikdorok.core_design.R.drawable.ic_noodle))
        3 -> setImageDrawable(ContextCompat.getDrawable(context, com.ddd.sikdorok.core_design.R.drawable.ic_salad))
        4 -> setImageDrawable(ContextCompat.getDrawable(context, com.ddd.sikdorok.core_design.R.drawable.ic_meat))
        5 -> setImageDrawable(ContextCompat.getDrawable(context, com.ddd.sikdorok.core_design.R.drawable.ic_bread))
        6 -> setImageDrawable(ContextCompat.getDrawable(context, com.ddd.sikdorok.core_design.R.drawable.ic_hamburger))
        7 -> setImageDrawable(ContextCompat.getDrawable(context, com.ddd.sikdorok.core_design.R.drawable.ic_sushi))
        8 -> setImageDrawable(ContextCompat.getDrawable(context, com.ddd.sikdorok.core_design.R.drawable.ic_cake))
    }
}