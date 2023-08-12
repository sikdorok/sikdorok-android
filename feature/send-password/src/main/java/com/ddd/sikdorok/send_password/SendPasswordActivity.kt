package com.ddd.sikdorok.send_password

import androidx.activity.viewModels
import com.ddd.sikdorok.send_password.databinding.ActivitySendPasswordBinding
import com.example.core_ui.base.BaseActivity
import com.example.core_ui.base.BaseViewModel

class SendPasswordActivity : BaseActivity<ActivitySendPasswordBinding>(ActivitySendPasswordBinding::inflate) {

    override val viewModel: BaseViewModel by viewModels()

    override fun initLayout() {
        val email = intent.getStringExtra(EMAIL)

        binding.tvDescription.text = getString(R.string.send_password_description, email.orEmpty())
    }

    override fun setupCollect() {}

    companion object {
        private const val EMAIL = "email"
    }
}
