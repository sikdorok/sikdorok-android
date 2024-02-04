package com.ddd.sikdorok.send_password

import androidx.activity.addCallback
import androidx.activity.viewModels
import com.ddd.sikdorok.core_ui.base.BaseActivity
import com.ddd.sikdorok.core_ui.base.BaseViewModel
import com.ddd.sikdorok.send_password.databinding.ActivitySendPasswordBinding
import com.ddd.sikdorok.splash.SplashNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SendPasswordActivity :
    BaseActivity<ActivitySendPasswordBinding>(ActivitySendPasswordBinding::inflate) {

    @Inject
    lateinit var splashNavigator: SplashNavigator

    override val viewModel: BaseViewModel by viewModels()

    override fun initLayout() {
        bind {
            val email = intent.getStringExtra(EMAIL)

            tvDescription.text =
                getString(R.string.send_password_description, email.orEmpty())

            confirm.setOnClickListener {
                goToSplash()
            }
        }

        onBackPressedDispatcher.addCallback {
            goToSplash()
        }
    }

    override fun setupCollect() {}

    private fun goToSplash() {
        startActivity(splashNavigator.start(this))
    }

    companion object {
        private const val EMAIL = "email"
    }
}
