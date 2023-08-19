package com.ddd.sikdorok.settings

import androidx.activity.viewModels
import com.ddd.sikdorok.core_ui.base.BaseActivity
import com.ddd.sikdorok.core_ui.base.BaseViewModel
import com.ddd.sikdorok.extensions.getPackageInfoCompat
import com.ddd.sikdorok.settings.databinding.ActivitySettingBinding

class SettingActivity : BaseActivity<ActivitySettingBinding>(ActivitySettingBinding::inflate) {

    override val viewModel: BaseViewModel by viewModels()

    override fun initLayout() {
        binding.tvVersion.text = getString(R.string.settings_app_versions, packageManager.getPackageInfoCompat(packageName).versionName)
    }

    override fun setupCollect() {
    }
}
