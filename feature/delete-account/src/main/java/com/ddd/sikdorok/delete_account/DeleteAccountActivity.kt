package com.ddd.sikdorok.delete_account

import android.widget.FrameLayout
import androidx.activity.viewModels
import com.ddd.sikdorok.core_ui.base.BackFrameActivity
import com.ddd.sikdorok.core_ui.base.BaseViewModel
import com.ddd.sikdorok.delete_account.databinding.ActivityDeleteAccountBinding

class DeleteAccountActivity : BackFrameActivity<ActivityDeleteAccountBinding>(ActivityDeleteAccountBinding::inflate) {

    override val backFrame: FrameLayout by lazy { binding.frameBack }

    override val viewModel: BaseViewModel by viewModels()

    override fun initLayout() {

    }

    override fun onClickBackFrameIcon() {
        finish()
    }

    override fun setupCollect() {
        
    }
}
