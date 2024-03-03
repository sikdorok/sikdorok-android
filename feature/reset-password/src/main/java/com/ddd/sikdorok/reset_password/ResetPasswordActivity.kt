package com.ddd.sikdorok.reset_password

import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ddd.sikdorok.core_design.R
import com.ddd.sikdorok.core_ui.base.BaseActivity
import com.ddd.sikdorok.extensions.showSnackBar
import com.ddd.sikdorok.extensions.textChanges
import com.ddd.sikdorok.navigator.signin.SignInNavigator
import com.ddd.sikdorok.reset_password.databinding.ActivityResetPasswordBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ResetPasswordActivity :
    BaseActivity<ActivityResetPasswordBinding>(ActivityResetPasswordBinding::inflate) {

    @Inject
    lateinit var signInNavigator: SignInNavigator

    override val viewModel: ResetPasswordViewModel by viewModels()
    val backFrame: FrameLayout by lazy { binding.frameBack }

    override fun initLayout() {
        binding.tvSubmit.isEnabled = false
        binding.tvSubmit.isSelected = false

        backFrame.setOnClickListener {
            viewModel.event(ResetPasswordContract.Event.OnClickLeftIcon)
        }

        binding.tvSubmit.setOnClickListener {
            viewModel.event(ResetPasswordContract.Event.Submit)
        }

        binding.inputPassword.textChanges()
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { password ->
                viewModel.event(ResetPasswordContract.Event.InputPassword(password.toString()))
            }
            .launchIn(lifecycleScope)

        binding.inputPasswordConfirm.textChanges()
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { passwordCheck ->
                viewModel.event(ResetPasswordContract.Event.InputPasswordConfirm(passwordCheck.toString()))
            }
            .launchIn(lifecycleScope)
    }

    override fun setupCollect() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                showLoading().takeIf { state.isLoading } ?: hideLoading()
            }
        }

        viewModel.effect
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { sideEffect ->
                when (sideEffect) {
                    ResetPasswordContract.SideEffect.NaviToBack -> {
                        finish()
                    }
                    is ResetPasswordContract.SideEffect.NaviToSuccess -> {
                        hideLoading()
                        startActivity(signInNavigator.start(context = this, isFromReset = true))
                    }
                    ResetPasswordContract.SideEffect.InValidatePassword -> {
                        hideLoading()
                        binding.tvPassword.error = "올바른 비밀번호를 입력해주세요"
                        binding.tvSubmit.isEnabled = false
                        binding.tvSubmit.isSelected = false
                    }
                    ResetPasswordContract.SideEffect.ValidatePassword -> {
                        binding.tvPassword.error = null
                        binding.tvSubmit.isEnabled = true
                        binding.tvSubmit.isSelected = true
                    }
                    is ResetPasswordContract.SideEffect.ShowSnackBar -> {
                        hideLoading()
                        showSnackBar(
                            view = binding.root,
                            message = sideEffect.message,
                            backgroundColor = R.color.input_error,
                            textColor = R.color.white,
                            duration = Snackbar.LENGTH_LONG
                        )
                    }
                }
            }
            .launchIn(lifecycleScope)

        viewModel.checkIsValidInfo()
    }
}
