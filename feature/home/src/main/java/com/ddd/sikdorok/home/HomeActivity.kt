package com.ddd.sikdorok.home

import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import com.ddd.sikdorok.core_design.R
import com.ddd.sikdorok.core_ui.base.BaseActivity
import com.ddd.sikdorok.core_ui.util.repeatCallDefaultOnStarted
import com.ddd.sikdorok.core_ui.util.show
import com.ddd.sikdorok.extensions.showSnackBar
import com.ddd.sikdorok.home.databinding.ActivityHomeBinding
import com.ddd.sikdorok.home.date.HomeDateAdapter
import com.ddd.sikdorok.home.dialog.HomeMonthlyDialog
import com.ddd.sikdorok.home.feed.HomeFeedAdapter
import com.ddd.sikdorok.navigator.modify.ModifyNavigator
import com.ddd.sikdorok.navigator.settings.SettingsNavigator
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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
            when (it.resultCode) {
                // TODO : 깔쌈하게...
                RESULT_CODE_CREATE -> {
                    showSnackBar("도시락 기록이 저장되었어요")
                    viewModel.getWeeklyMealboxInfo()
                }
                RESULT_CODE_MODIFY -> {
                    showSnackBar("도시락 기록이 수정되었어요")
                    viewModel.getWeeklyMealboxInfo()
                }
                RESULT_CODE_DELETE -> {
                    showSnackBar("도시락 기록이 삭제되었어요")
                    viewModel.getWeeklyMealboxInfo()
                }
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

        onBackPressedDispatcher.addCallback {
            finish()
        }
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
                    modifyLauncher.launch(
                        modifyNavigator.start(
                            this,
                            effect.id,
                            effect.postDate
                        )
                    )
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

        repeatCallDefaultOnStarted {
            viewModel.state.collect {
                if(it.isLoading) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }
    }

    private fun initDeepLink() {
        // TODO : 마이그레이션
        intent.getStringExtra(KEY_DEEPLINK)?.let {
            viewModel.event(HomeContract.Event.DeepLink(it))
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.getWeeklyMealboxInfo()
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

    private fun showSnackBar(text: String) {
        showSnackBar(
            view = binding.root,
            message = text,
            backgroundColor = R.color.email_login_background,
            textColor = R.color.white,
            duration = Snackbar.LENGTH_LONG
        )
    }

    companion object {
        const val KEY_DEEPLINK = "deeplink"
        const val KEY_NOW_DATE = "now_date"

        const val RESULT_CODE_CREATE = 3000
        const val RESULT_CODE_MODIFY = 3001
        const val RESULT_CODE_DELETE = 3002
    }
}