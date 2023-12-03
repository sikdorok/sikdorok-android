package com.ddd.sikdorok.core_ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.ViewDataBinding

abstract class BackFrameActivity<T: ViewDataBinding>(inflater: (LayoutInflater) -> T) : BaseActivity<T>(inflater) {

    abstract val backFrame: FrameLayout

    open fun onClickBackFrameIcon() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        backFrame.setOnClickListener {
            onClickBackFrameIcon()
        }
    }

}
