package com.ddd.sikdorok.settings

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ddd.sikdorok.core_ui.util.bindImageRes

@BindingAdapter("bind:is_need_update")
fun AppCompatTextView.bindIsNeedUpdate(isNeedUpdate: Boolean?) {
    if (isNeedUpdate == true) {
        setTextColor(ContextCompat.getColor(context, com.ddd.sikdorok.core_design.R.color.green))
        text = "버전 업데이트"
    } else {
        setTextColor(ContextCompat.getColor(context, com.ddd.sikdorok.core_design.R.color.gray3))
        text = "최신 버전입니다"
    }
}

@BindingAdapter("bind:user_image")
fun AppCompatImageView.bindUserImage(
    userImage: Drawable?
) {
    if(userImage != null) {
        Glide.with(this.context)
            .load(userImage)
            .centerCrop()
            .circleCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }
}