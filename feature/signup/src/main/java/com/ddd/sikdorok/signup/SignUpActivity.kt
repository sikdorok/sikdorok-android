package com.ddd.sikdorok.signup

import android.app.Activity
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ddd.sikdorok.extensions.textChanges
import com.ddd.sikdorok.signup.databinding.ActivitySignUpBinding
import com.example.core_ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding>(ActivitySignUpBinding::inflate) {

    override val viewModel: SignUpViewModel by viewModels()
    private val email by lazy { intent.extras?.getString(PAYLOAD) }

    override fun initLayout() {


        if(email.orEmpty().isNotEmpty()) {
            binding.editEmail.isEnabled = false
            binding.editEmail.setText(email)
        }

        binding.tvSubmit.setOnClickListener {
            viewModel.event(SignUpContract.Event.SignUp(
                binding.editName.text.toString(),
                binding.editEmail.text.toString(),
                binding.editPassword.text.toString(),
                binding.editPasswordCheck.text.toString()
            ))
        }

        binding.frameClose.setOnClickListener {
            viewModel.event(SignUpContract.Event.OnBackPressed)
        }
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
                when(sideEffect) {
                    is SignUpContract.SideEffect.ValidateName -> {
                        binding.inputName.error = null
                    }
                    is SignUpContract.SideEffect.InValidateName -> {
                        binding.inputName.error = getString(R.string.name_error)
                    }
                    is SignUpContract.SideEffect.InValidatePassword -> {
                        binding.inputPassword.error = "8자 이상, 20자 이하로 입력해주세요"
                    }
                    is SignUpContract.SideEffect.ValidatePassword -> {
                        binding.inputPassword.error = null
                    }
                    is SignUpContract.SideEffect.ValidatePasswordCheck -> {
                        binding.inputPasswordCheck.error = null
                    }
                    is SignUpContract.SideEffect.InValidatePasswordCheck -> {
                        binding.inputPasswordCheck.error = "입력하신 비밀번호가 일치하지 않습니다"
                    }
                    is SignUpContract.SideEffect.NaviToHome -> {
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                    is SignUpContract.SideEffect.NaviToBack -> {
                        finish()
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
    }
}
