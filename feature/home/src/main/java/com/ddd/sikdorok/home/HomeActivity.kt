package com.ddd.sikdorok.home

import android.app.Activity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import com.ddd.sikdorok.core_ui.base.BaseActivity
import com.ddd.sikdorok.core_ui.util.repeatCallDefaultOnStarted
import com.ddd.sikdorok.core_ui.util.show
import com.ddd.sikdorok.home.databinding.ActivityHomeBinding
import com.ddd.sikdorok.home.date.HomeDateAdapter
import com.ddd.sikdorok.home.dialog.HomeMonthlyDialog
import com.ddd.sikdorok.home.feed.HomeFeedAdapter
import com.ddd.sikdorok.navigator.modify.ModifyNavigator
import com.ddd.sikdorok.navigator.settings.SettingsNavigator
import dagger.hilt.android.AndroidEntryPoint
import org.joda.time.DateTime
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {

    @Inject
    lateinit var modifyNavigator: ModifyNavigator

    @Inject
    lateinit var homeListNavigator: HomeListNavigator

    @Inject
    lateinit var settingsNavigator: SettingsNavigator

    private val modifyLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                viewModel.getWeeklyMealboxInfo()
            }
        }

    override val viewModel: HomeViewModel by viewModels()

    override fun initLayout() {
        bind {
            vm = viewModel
            lifecycleOwner = this@HomeActivity

            listWeekly.adapter = HomeDateAdapter {
                viewModel.event(HomeContract.Event.Click.Date(it))
            }

            listMealbox.adapter = HomeFeedAdapter {
                viewModel.event(HomeContract.Event.Click.Feed(it))
            }
        }

        initDeepLink()
        viewModel.getWeeklyMealboxInfo()
    }

    override fun setupCollect() {
        fun move(effect: HomeContract.Effect.Move) {
            when (effect) {
                is HomeContract.Effect.Move.ChangeDate -> {
                    goToChangeDate(effect.nowDate)
                }
                is HomeContract.Effect.Move.DeepLink -> {
                    // TODO : Handle DeepLink
                }
                is HomeContract.Effect.Move.ListPage -> {
                    startActivity(homeListNavigator.start(this, effect.selectedDate))
                }
                HomeContract.Effect.Move.Setting -> {
                    startActivity(settingsNavigator.start(this))
                }
                is HomeContract.Effect.Move.Feed -> {
                    modifyLauncher.launch(modifyNavigator.start(this, effect.id))
                }
            }
        }

        repeatCallDefaultOnStarted {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is HomeContract.Effect.Move -> {
                        move(effect)
                    }
                }
            }
        }
    }

    private fun initDeepLink() {
        intent.getStringExtra(KEY_DEEPLINK)?.let {
            viewModel.event(HomeContract.Event.DeepLink(it))
        }
    }

    private fun goToChangeDate(nowDate: DateTime) {
        HomeMonthlyDialog.newInstance()
            .apply {
                arguments = bundleOf(
                    KEY_NOW_DATE to nowDate.toString("yyyy-MM-dd")
                )
            }
            .show(supportFragmentManager)
    }

    companion object {
        const val KEY_DEEPLINK = "deeplink"
        const val KEY_NOW_DATE = "now_date"
    }
}