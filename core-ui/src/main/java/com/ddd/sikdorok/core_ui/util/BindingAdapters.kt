package com.ddd.sikdorok.core_ui.util

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ddd.sikdorok.core_ui.R
import com.ddd.sikdorok.shared.code.Icon

@BindingAdapter("bind:visibility")
fun View.bindVisibleOrGone(isVisibleOrGone: Boolean?) {
    visibility = if (isVisibleOrGone == true) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("bind:visibility")
fun View.bindVisibleOrGone(text: String?) {
    visibility = if (!text.isNullOrEmpty()) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("bind:selected")
fun View.bindIsSelected(isSelected: Boolean?) {
    this.isSelected = isSelected ?: false
}

@BindingAdapter("bind:list_item")
fun RecyclerView.bindListItems(list: List<Any>?) {
    if (adapter != null) {
        (adapter as ListAdapter<Any, *>).submitList(list ?: emptyList())
    }
}

@BindingAdapter("bind:srcUrl")
fun AppCompatImageView.bindImageRes(srcUrl: String?) {
    if (!srcUrl.isNullOrEmpty()) {
        Glide.with(this.context)
            .load(srcUrl)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }
}


@BindingAdapter("bind:meal_icon")
fun AppCompatImageView.bindMealIcon(icon: String?) {
    if (!icon.isNullOrEmpty()) {

        val res = when (Icon.values().firstOrNull { it.code == icon }) {
            Icon.NOTHING -> com.ddd.sikdorok.core_design.R.drawable.ic_nothing_selected
            Icon.RICE -> com.ddd.sikdorok.core_design.R.drawable.ic_selected_cooked_rice
            Icon.NOODLE -> com.ddd.sikdorok.core_design.R.drawable.ic_selected_steaming_bowl_noodle
            Icon.SALAD -> com.ddd.sikdorok.core_design.R.drawable.ic_selected_green_salad
            Icon.MEAT -> com.ddd.sikdorok.core_design.R.drawable.ic_selected_on_bone_meat
            Icon.BREAD -> com.ddd.sikdorok.core_design.R.drawable.ic_selected_bread
            Icon.FAST_FOOD -> com.ddd.sikdorok.core_design.R.drawable.ic_selected_hamburger
            Icon.SUSHI -> com.ddd.sikdorok.core_design.R.drawable.ic_selected_sushi
            Icon.CAKE -> com.ddd.sikdorok.core_design.R.drawable.ic_shortcake
            else -> return
        }

        Glide.with(this.context)
            .load(res)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }
}