package com.ddd.sikdorok.signin

import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ddd.sikdorok.core_design.R as coreDesignR
import com.ddd.sikdorok.find_password.FindPasswordNavigator
import com.ddd.sikdorok.signin.databinding.ActivitySignInBinding
import com.ddd.sikdorok.signup.SignUpNavigator
import com.ddd.sikdorok.core_ui.base.BackFrameActivity
import com.ddd.sikdorok.extensions.showSnackBar
import com.ddd.sikdorok.home.HomeNavigator
import com.google.android.material.snackbar.Snackbar
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
    @Inject
    lateinit var homeNavigator: HomeNavigator

    override val viewModel: SignInViewModel by viewModels()
    override val backFrame: FrameLayout by lazy { binding.frameBack }

    override fun initLayout() {
        binding.tvFindPassword.setOnClickListener {
            viewModel.event(SignInContract.Event.NaviToFindPassword)
        }

        binding.tvSignUp.setOnClickListener {
            viewModel.event(SignInContract.Event.NaviToSignUp)
        }

        binding.tvLogin.setOnClickListener {
            viewModel.event(
                SignInContract.Event.OnClickSubmit(
                    email = binding.editEmail.text.toString(),
                    password = binding.editPassword.text.toString()
                )
            )
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
                    is SignInContract.SideEffect.NaviToHome -> {
                        startActivity(homeNavigator.start(this))
                        finish()
                    }
                    is SignInContract.SideEffect.ShowSnackBar -> {
                        showSnackBar(
                            view = binding.view,
                            message = it.message,
                            backgroundColor = coreDesignR.color.input_error,
                            textColor = coreDesignR.color.white,
                            duration = Snackbar.LENGTH_LONG)
                    }
                }
            }
            .launchIn(lifecycleScope)
    }
}
