package com.ddd.sikdorok.find_password

import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ddd.sikdorok.core_design.R
import com.ddd.sikdorok.core_ui.base.BackFrameActivity
import com.ddd.sikdorok.core_ui.util.KeyboardUtil
import com.ddd.sikdorok.extensions.showSnackBar
import com.ddd.sikdorok.extensions.textChanges
import com.ddd.sikdorok.find_password.databinding.ActivityFindPasswordBinding
import com.ddd.sikdorok.send_password.SendPasswordNavigator
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class FindPasswordActivity :
    BackFrameActivity<ActivityFindPasswordBinding>(ActivityFindPasswordBinding::inflate) {

    @Inject
    lateinit var sendPasswordNavigator: SendPasswordNavigator

    override val viewModel: FindPasswordViewModel by viewModels()
    override val backFrame: FrameLayout by lazy { binding.frameBack }

    override fun initLayout() {
        binding.editEmail.textChanges()
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { text ->
                viewModel.event(FindPasswordContract.Event.InputEmail(text.toString()))
            }
            .launchIn(lifecycleScope)

        binding.tvSubmit.setOnClickListener {
            showLoading()
            KeyboardUtil.downKeyboard(this, binding.editEmail)
            
            viewModel.event(FindPasswordContract.Event.Submit(binding.editEmail.text.toString()))
        }

        binding.tvSubmit.isEnabled = false
        binding.tvSubmit.isSelected = false
    }

    override fun onClickBackFrameIcon() {
        viewModel.event(FindPasswordContract.Event.OnClickLeftIcon)
    }

    override fun setupCollect() {
        viewModel.effect
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { sideEffect ->
                when (sideEffect) {
                    FindPasswordContract.SideEffect.NaviToBack -> {
                        finish()
                    }
                    is FindPasswordContract.SideEffect.NaviToSuccess -> {
                        hideLoading()
                        startActivity(sendPasswordNavigator.start(this, sideEffect.email))
                    }
                    FindPasswordContract.SideEffect.InValidateEmail -> {
                        hideLoading()
                        binding.inputEmail.error = "올바른 이메일 주소를 입력해주세요"
                        binding.tvSubmit.isEnabled = false
                        binding.tvSubmit.isSelected = false
                    }
                    FindPasswordContract.SideEffect.ValidateEmail -> {
                        binding.inputEmail.error = null
                        binding.tvSubmit.isEnabled = true
                        binding.tvSubmit.isSelected = true
                    }
                    is FindPasswordContract.SideEffect.ShowSnackBar -> {
                        hideLoading()
                        showSnackBar(
                            view = binding.root,
                            message = sideEffect.message,
                            backgroundColor = R.color.input_error,
                            textColor = R.color.white,
                            duration = Snackbar.LENGTH_SHORT
                        )
                    }
                }
            }
            .launchIn(lifecycleScope)
    }
}
