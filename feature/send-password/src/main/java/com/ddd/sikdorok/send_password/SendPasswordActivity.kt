package com.ddd.sikdorok.send_password

import android.widget.FrameLayout
import androidx.activity.viewModels
import com.ddd.sikdorok.send_password.databinding.ActivitySendPasswordBinding
import com.ddd.sikdorok.core_ui.base.BackFrameActivity
import com.ddd.sikdorok.core_ui.base.BaseViewModel

class SendPasswordActivity : BackFrameActivity<ActivitySendPasswordBinding>(ActivitySendPasswordBinding::inflate) {

    override val viewModel: BaseViewModel by viewModels()
    override val backFrame: FrameLayout by lazy { binding.frameBack }

    override fun initLayout() {
        val email = intent.getStringExtra(EMAIL)

        binding.tvDescription.text = getString(R.string.send_password_description, email.orEmpty())
    }

    override fun onClickBackFrameIcon() {
        //TODO
    }

    override fun setupCollect() {}

    companion object {
        private const val EMAIL = "email"
    }
}
