package com.ddd.sikdorok.settings

import android.content.Intent
import android.net.Uri
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ddd.sikdorok.core_ui.base.BackFrameActivity
import com.ddd.sikdorok.extensions.getPackageInfoCompat
import com.ddd.sikdorok.navigator.delete_account.DeleteAccountNavigator
import com.ddd.sikdorok.settings.databinding.ActivitySettingBinding
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
                }
            }
            .launchIn(lifecycleScope)
    }
}
