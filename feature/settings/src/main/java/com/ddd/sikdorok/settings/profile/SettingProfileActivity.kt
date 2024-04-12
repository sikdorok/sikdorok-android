package com.ddd.sikdorok.settings.profile

import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ddd.sikdorok.core_ui.base.BackFrameActivity
import com.ddd.sikdorok.extensions.showSnackBar
import com.ddd.sikdorok.extensions.textChanges
import com.ddd.sikdorok.settings.databinding.ActivitySettingsProfileBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.ddd.sikdorok.core_design.R as coreDesignR

@AndroidEntryPoint
class SettingProfileActivity :
    BackFrameActivity<ActivitySettingsProfileBinding>(ActivitySettingsProfileBinding::inflate) {

    override val viewModel: SettingProfileViewModel by viewModels()

    override val backFrame: FrameLayout by lazy { binding.frameClose }

    override fun initLayout() {

        binding.editEmail.isEnabled = false

        binding.confirm.setOnClickListener {
            showLoading()

            viewModel.event(
                SettingProfileContract.Event.EditProfile(
                    name = binding.editName.text.toString()
                )
            )
        }

        viewModel.getUserProfile()
    }

    override fun onClickBackFrameIcon() {
        viewModel.event(SettingProfileContract.Event.OnBackPressed)
    }

    override fun setupCollect() {
        binding.editName.textChanges()
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .debounce(1000)
            .onEach { name ->
                viewModel.event(SettingProfileContract.Event.NameCheck(name.toString()))
            }
            .launchIn(lifecycleScope)

        viewModel.effect
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { sideEffect ->
                when (sideEffect) {
                    SettingProfileContract.SideEffect.NaviToSuccess -> {
                        setResult(100)
                        finish()
                    }
                    is SettingProfileContract.SideEffect.ValidateName -> {
                        binding.inputName.error = null
                    }
                    is SettingProfileContract.SideEffect.InValidateName -> {
                        binding.inputName.error = "2자 이상, 10자 미만으로 입력해 주세요"
                    }
                    is SettingProfileContract.SideEffect.NaviToBack -> {
                        finish()
                    }
                    is SettingProfileContract.SideEffect.SnowSnackBar -> {
                        // TODO : ?????
                        hideLoading()

                        showSnackBar(
                            view = binding.root,
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
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { state ->
                showLoading().takeIf { state.isLoading } ?: hideLoading()

                binding.editName.setText(state.name)
                binding.editEmail.setText(state.email)
            }.launchIn(lifecycleScope)
    }
}
