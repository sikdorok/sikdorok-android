package com.ddd.sikdorok.delete_account

import android.widget.FrameLayout
import androidx.activity.viewModels
import com.ddd.sikdorok.core_ui.base.BackFrameActivity
import com.ddd.sikdorok.core_ui.base.BaseViewModel
import com.ddd.sikdorok.delete_account.databinding.ActivityDeleteAccountBinding
import com.ddd.sikdorok.extensions.RepeatCount
import com.ddd.sikdorok.extensions.RepeatMode
import com.ddd.sikdorok.extensions.getTranslateFloatAnimate

class DeleteAccountActivity : BackFrameActivity<ActivityDeleteAccountBinding>(ActivityDeleteAccountBinding::inflate) {

    override val backFrame: FrameLayout by lazy { binding.frameBack }

    override val viewModel: BaseViewModel by viewModels()

    private val floatAnimate by lazy {
        binding.ivTooltip.getTranslateFloatAnimate(
            600L,
            RepeatCount.INFINITE,
            RepeatMode.REVERSE,
            extraValue = 10f,
            updateListener = { view, newValue -> view.translationY = newValue }
        )
    }

    override fun initLayout() {
        floatAnimate.start()
    }

    override fun onClickBackFrameIcon() {
        finish()
    }

    override fun setupCollect() {
        
    }

    override fun onDestroy() {
        floatAnimate.end()
        super.onDestroy()
    }
}
