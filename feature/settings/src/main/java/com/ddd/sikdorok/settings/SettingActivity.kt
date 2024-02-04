package com.ddd.sikdorok.settings

import android.content.Intent
import android.net.Uri
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ddd.sikdorok.core_ui.base.BackFrameActivity
import com.ddd.sikdorok.core_ui.util.makeAlertDialog
import com.ddd.sikdorok.extensions.getPackageInfoCompat
import com.ddd.sikdorok.extensions.showSnackBar
import com.ddd.sikdorok.navigator.delete_account.DeleteAccountNavigator
import com.ddd.sikdorok.settings.databinding.ActivitySettingBinding
import com.ddd.sikdorok.splash.SplashNavigator
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class SettingActivity : BackFrameActivity<ActivitySettingBinding>(ActivitySettingBinding::inflate) {

    override val viewModel: SettingsViewModel by viewModels()

    override val backFrame: FrameLayout by lazy {
        binding.frameBack
    }

    @Inject
    lateinit var splashNavigator: SplashNavigator

    @Inject
    lateinit var deleteAccountNavigator: DeleteAccountNavigator

    override fun initLayout() {
        bind {
            vm = viewModel
            lifecycleOwner = this@SettingActivity

            binding.cardPolicy.setOnClickListener {
                viewModel.event(SettingsContract.Event.OnClickPolicy)
            }
            binding.cardProfileManage.setOnClickListener {
                viewModel.event(SettingsContract.Event.OnClickProfileManage)
            }
            binding.cardProfileDelete.setOnClickListener {
                viewModel.event(SettingsContract.Event.OnClickAccountDelete)
            }
        }

        binding.tvVersion.text = getString(
            R.string.settings_app_versions,
            packageManager.getPackageInfoCompat(packageName).versionName
        )

        viewModel.getUserDeviceInfo(packageManager.getPackageInfoCompat(packageName).versionName)
    }

    override fun setupCollect() {
        viewModel.effect
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { sideEffect ->
                when (sideEffect) {
                    SettingsContract.SideEffect.NaviToProfileManage -> Unit
                    SettingsContract.SideEffect.NaviToPolicy -> {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.notion.so/972546495d1941b5a50d23498ccf8ef3?pvs=4")
                        )

                        startActivity(intent)
                    }
                    SettingsContract.SideEffect.NaviToDeleteAccount -> {
                        startActivity(deleteAccountNavigator.start(this))
                    }
                    SettingsContract.SideEffect.Logout -> {
                        makeAlertDialog(
                            title = "로그아웃 하시겠습니까?",
                            confirmText = "네",
                            cancelText = "아니오",
                            onConfirm = {
                                viewModel.setUserLogout()
                            }
                        )
                    }
                    SettingsContract.SideEffect.NaviToSplash -> {
                        startActivity(splashNavigator.start(this))
                    }
                    is SettingsContract.SideEffect.Fail -> {
                        showSnackBar(
                            view = binding.root,
                            message = sideEffect.errorMsg,
                            backgroundColor = com.ddd.sikdorok.core_design.R.color.text_color,
                            textColor = com.ddd.sikdorok.core_design.R.color.white,
                            duration = Snackbar.LENGTH_LONG
                        )
                    }
                }
            }
            .launchIn(lifecycleScope)
    }
}
