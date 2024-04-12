package com.ddd.sikdorok.signup

import android.app.Activity
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ddd.sikdorok.core_ui.base.BackFrameActivity
import com.ddd.sikdorok.extensions.showSnackBar
import com.ddd.sikdorok.extensions.textChanges
import com.ddd.sikdorok.signup.databinding.ActivitySignUpBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.ddd.sikdorok.core_design.R as coreDesignR

@AndroidEntryPoint
class SignUpActivity : BackFrameActivity<ActivitySignUpBinding>(ActivitySignUpBinding::inflate) {

    private val email: String? by lazy {
        intent.extras?.getString(PAYLOAD)
    }

    private val oauthId: Long? by lazy {
        intent.extras?.getLong(KEY_OAUTH_ID)
    }
    private val oauthType: String? by lazy {
        intent.extras?.getString(KEY_OAUTH_TYPE)
    }

    override val viewModel: SignUpViewModel by viewModels()

    override val backFrame: FrameLayout by lazy { binding.frameClose }

    override fun initLayout() {
        if (!email.isNullOrEmpty()) {
            binding.editEmail.isEnabled = false
            binding.editEmail.setText(email)
        }

        binding.tvSubmit.setOnClickListener {
            showLoading()

            viewModel.event(
                SignUpContract.Event.SignUp(
                    oauthType = oauthType,
                    oauthId = oauthId,
                    nickname = binding.editName.text.toString(),
                    email = binding.editEmail.text.toString(),
                    password = binding.editPassword.text.toString(),
                    passwordCheck = binding.editPasswordCheck.text.toString()
                )
            )
        }
    }

    override fun onClickBackFrameIcon() {
        viewModel.event(SignUpContract.Event.OnBackPressed)
    }

    override fun setupCollect() {
        binding.editEmail.textChanges()
            .filter { binding.editEmail.isEnabled || it.isNotEmpty() }
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .debounce(1000L)
            .onEach { viewModel.event(SignUpContract.Event.EmailCheck(it.toString())) }
            .launchIn(lifecycleScope)

        binding.editName.textChanges()
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { name ->
                viewModel.event(SignUpContract.Event.InputName(name.toString()))
            }
            .launchIn(lifecycleScope)

        binding.editPassword.textChanges()
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { password ->
                viewModel.event(SignUpContract.Event.InputPassword(password.toString()))
            }
            .launchIn(lifecycleScope)

        binding.editPasswordCheck.textChanges()
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { passwordCheck ->
                viewModel.event(SignUpContract.Event.InputPasswordCheck(passwordCheck.toString()))
            }
            .launchIn(lifecycleScope)

        viewModel.effect
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { sideEffect ->
                when (sideEffect) {
                    is SignUpContract.SideEffect.ValidateEmail -> {
                        binding.inputEmail.error = null
                    }
                    SignUpContract.SideEffect.InValidateEmailFormat -> {
                        binding.inputEmail.error = getString(R.string.email_error)
                    }
                    SignUpContract.SideEffect.AlreadyRegisteredEmail -> {
                        binding.inputEmail.error = getString(R.string.email_error_registered)
                    }
                    is SignUpContract.SideEffect.ValidateName -> {
                        binding.inputName.error = null
                    }
                    is SignUpContract.SideEffect.InValidateName -> {
                        binding.inputName.error = getString(R.string.name_error)
                    }
                    is SignUpContract.SideEffect.InValidatePassword -> {
                        binding.inputPassword.error = getString(R.string.password_error)
                    }
                    is SignUpContract.SideEffect.ValidatePassword -> {
                        binding.inputPassword.error = null
                    }
                    is SignUpContract.SideEffect.ValidatePasswordCheck -> {
                        binding.inputPasswordCheck.error = null
                    }
                    is SignUpContract.SideEffect.InValidatePasswordCheck -> {
                        binding.inputPasswordCheck.error = getString(R.string.password_check_error)
                    }
                    is SignUpContract.SideEffect.NaviToHome -> {
                        hideLoading()

                        // TODO : 스낵바 도출 필요
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                    is SignUpContract.SideEffect.NaviToBack -> {
                        finish()
                    }
                    is SignUpContract.SideEffect.SnowSnackBar -> {
                        // TODO : ?????
                        hideLoading()

                        showSnackBar(
                            view = binding.tvSubmit,
                            message = sideEffect.message,
                            backgroundColor = coreDesignR.color.input_error,
                            textColor = coreDesignR.color.white,
                            duration = Snackbar.LENGTH_SHORT
                        )
                    }
                }
            }
            .launchIn(lifecycleScope)

        viewModel.state
            .onEach {
                viewModel.event(SignUpContract.Event.PasswordCheck(it.password == it.passwordCheck))


                binding.tvSubmit.isEnabled = it.isValidate
            }.launchIn(lifecycleScope)
    }

    companion object {
        private const val PAYLOAD = "payload"
        private const val KEY_OAUTH_ID = "oauthId"
        private const val KEY_OAUTH_TYPE = "oauthType"
    }
}
