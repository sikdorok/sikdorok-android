package com.ddd.sikdorok.signin

import android.app.Activity
import android.widget.FrameLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ddd.sikdorok.core_ui.base.BackFrameActivity
import com.ddd.sikdorok.core_ui.util.KeyboardUtil
import com.ddd.sikdorok.extensions.showSnackBar
import com.ddd.sikdorok.find_password.FindPasswordNavigator
import com.ddd.sikdorok.home.HomeNavigator
import com.ddd.sikdorok.signin.databinding.ActivitySignInBinding
import com.ddd.sikdorok.signup.SignUpNavigator
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.ddd.sikdorok.core_design.R as coreDesignR

@AndroidEntryPoint
class SignInActivity : BackFrameActivity<ActivitySignInBinding>(ActivitySignInBinding::inflate) {

    @Inject
    lateinit var signUpNavigator: SignUpNavigator

    @Inject
    lateinit var findPasswordNavigator: FindPasswordNavigator

    @Inject
    lateinit var homeNavigator: HomeNavigator

    private val signinLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                showSnackBar(
                    view = binding.view,
                    message = "회원가입이 완료되었습니다.",
                    backgroundColor = coreDesignR.color.text_color,
                    textColor = coreDesignR.color.white,
                    duration = Snackbar.LENGTH_LONG
                )
            }
        }

    override val viewModel: SignInViewModel by viewModels()

    override val backFrame: FrameLayout by lazy { binding.frameBack }

    override fun initLayout() {
        if (viewModel.isFromEdit) {
            showSnackBar(
                view = binding.view,
                message = "비밀번호 재설정이 완료되었습니다",
                backgroundColor = coreDesignR.color.text_color,
                textColor = coreDesignR.color.white,
                duration = Snackbar.LENGTH_LONG
            )
        }

        binding.tvFindPassword.setOnClickListener {
            viewModel.event(SignInContract.Event.NaviToFindPassword)
        }

        binding.tvSignUp.setOnClickListener {
            viewModel.event(SignInContract.Event.NaviToSignUp)
        }

        binding.tvLogin.setOnClickListener {
            showLoading()

            KeyboardUtil.downKeyboard(this, binding.editEmail)
            KeyboardUtil.downKeyboard(this, binding.editPassword)

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
                when (it) {
                    is SignInContract.SideEffect.NaviToSignUp -> {
                        signinLauncher.launch(
                            signUpNavigator.start(this)
                        )
                    }
                    is SignInContract.SideEffect.NaviToFindPassword -> {
                        startActivity(findPasswordNavigator.start(this))
                    }
                    is SignInContract.SideEffect.NaviToBack -> {
                        finish()
                    }
                    is SignInContract.SideEffect.NaviToHome -> {
                        hideLoading()

                        startActivity(homeNavigator.start(this))
                        finish()
                    }
                    is SignInContract.SideEffect.ShowSnackBar -> {
                        hideLoading()

                        showSnackBar(
                            view = binding.view,
                            message = it.message,
                            backgroundColor = coreDesignR.color.input_error,
                            textColor = coreDesignR.color.white,
                            duration = Snackbar.LENGTH_LONG
                        )
                    }
                }
            }
            .launchIn(lifecycleScope)
    }
}
