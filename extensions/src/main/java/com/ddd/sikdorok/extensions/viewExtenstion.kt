package com.ddd.sikdorok.extensions

import android.animation.ValueAnimator
import android.view.View
import androidx.annotation.IntRange

fun View.getTranslateFloatAnimate(
    @IntRange(from = 500L, to = 2000L) duration: Long,
    repeatCount: RepeatCount,
    repeatMode: RepeatMode,
    extraValue: Float,
    updateListener: (View, Float) -> Unit
): ValueAnimator {
    val endValue = translationY + extraValue

    return ValueAnimator.ofFloat(translationY, endValue)
        .apply {
            this.duration = duration
            this.repeatMode = repeatMode.value
            this.repeatCount = repeatCount.value
        }.also {
            it.addUpdateListener { animator ->
                updateListener.invoke(this, animator.animatedValue as Float)
            }
        }
}

enum class RepeatCount(val value: Int) {
    INFINITE(ValueAnimator.INFINITE),
    NONE(0)
}

enum class RepeatMode(val value: Int) {
    RESTART(ValueAnimator.RESTART),
    REVERSE(ValueAnimator.REVERSE)
}
