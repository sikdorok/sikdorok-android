package com.ddd.sikdorok.delete_account

import android.widget.FrameLayout
import androidx.activity.viewModels
import com.ddd.sikdorok.core_design.R
import com.ddd.sikdorok.core_ui.base.BackFrameActivity
import com.ddd.sikdorok.core_ui.util.repeatCallDefaultOnStarted
import com.ddd.sikdorok.delete_account.databinding.ActivityDeleteAccountBinding
import com.ddd.sikdorok.extensions.RepeatCount
import com.ddd.sikdorok.extensions.RepeatMode
import com.ddd.sikdorok.extensions.getTranslateFloatAnimate
import com.ddd.sikdorok.extensions.showSnackBar
import com.ddd.sikdorok.navigator.login.LoginNavigator
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class DeleteAccountActivity :
    BackFrameActivity<ActivityDeleteAccountBinding>(ActivityDeleteAccountBinding::inflate) {

    @Inject
    lateinit var loginNavigator: LoginNavigator

    override val backFrame: FrameLayout by lazy { binding.frameBack }

    override val viewModel: DeleteAccountViewModel by viewModels()

    private val floatAnimate by lazy {
        binding.ivTooltip.getTranslateFloatAnimate(
            600L,
            RepeatCount.INFINITE,
            RepeatMode.REVERSE,
            extraValue = 10f,
            updateListener = { view, newValue -> view.translationY = newValue }
        )
    }

    override fun initLayout() {
        floatAnimate.start()

        binding.tvSubmit.setOnClickListener {
            viewModel.event(DeleteAccountContract.Event.OnClickDeleteAccount)
        }
    }

    override fun onClickBackFrameIcon() {
        finish()
    }

    override fun setupCollect() {
        repeatCallDefaultOnStarted {
            viewModel.state.collectLatest { state ->
                showLoading().takeIf { state.isLoading } ?: hideLoading()
            }
        }

        repeatCallDefaultOnStarted {
            viewModel.effect.collectLatest { effect ->
                when (effect) {
                    DeleteAccountContract.Effect.GoToMain -> {
                        startActivity(
                            loginNavigator.start(
                                context = this@DeleteAccountActivity,
                                isFromDelete = true
                            )
                        )
                    }
                    is DeleteAccountContract.Effect.ShowSnackBar -> {
                        showSnackBar(
                            view = binding.view,
                            message = effect.text,
                            backgroundColor = R.color.text_color,
                            textColor = R.color.white,
                            duration = Snackbar.LENGTH_SHORT
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        floatAnimate.end()
        super.onDestroy()
    }
}
