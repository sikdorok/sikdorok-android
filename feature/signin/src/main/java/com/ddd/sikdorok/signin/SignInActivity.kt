package com.ddd.sikdorok.signin

import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ddd.sikdorok.find_password.FindPasswordNavigator
import com.ddd.sikdorok.signin.databinding.ActivitySignInBinding
import com.ddd.sikdorok.signup.SignUpNavigator
import com.ddd.sikdorok.core_ui.base.BackFrameActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class SignInActivity : BackFrameActivity<ActivitySignInBinding>(ActivitySignInBinding::inflate) {

    @Inject
    lateinit var signUpNavigator: SignUpNavigator
    @Inject
    lateinit var findPasswordNavigator: FindPasswordNavigator

    override val viewModel: SignInViewModel by viewModels()
    override val backFrame: FrameLayout by lazy { binding.frameBack }

    override fun initLayout() {
        binding.tvFindPassword.setOnClickListener {
            viewModel.event(SignInContract.Event.NaviToFindPassword)
        }

        binding.tvSignUp.setOnClickListener {
            viewModel.event(SignInContract.Event.NaviToSignUp)
        }
    }

    override fun onClickBackFrameIcon() {
        viewModel.event(SignInContract.Event.OnBackPressed)
    }

    override fun setupCollect() {
        viewModel.effect
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach {
                when(it) {
                    is SignInContract.SideEffect.NaviToSignUp -> {
                        startActivity(signUpNavigator.start(this))
                    }
                    is SignInContract.SideEffect.NaviToFindPassword -> {
                        startActivity(findPasswordNavigator.start(this))
                    }
                    is SignInContract.SideEffect.NaviToBack -> {
                        finish()
                    }
                }
            }
            .launchIn(lifecycleScope)
    }
}
