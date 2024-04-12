package com.ddd.sikdorok.home.list

import android.widget.FrameLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import com.ddd.sikdorok.core_design.R
import com.ddd.sikdorok.core_ui.base.BackFrameActivity
import com.ddd.sikdorok.core_ui.util.repeatCallDefaultOnStarted
import com.ddd.sikdorok.core_ui.util.show
import com.ddd.sikdorok.extensions.showSnackBar
import com.ddd.sikdorok.home.HomeActivity
import com.ddd.sikdorok.home.databinding.ActivityHomeListBinding
import com.ddd.sikdorok.home.list.dialog.HomeListMonthlyDialog
import com.ddd.sikdorok.home.list.feed.HomeListFeedAdapter
import com.ddd.sikdorok.navigator.modify.ModifyNavigator
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.joda.time.DateTime
import javax.inject.Inject

@AndroidEntryPoint
class HomeListActivity :
    BackFrameActivity<ActivityHomeListBinding>(ActivityHomeListBinding::inflate) {

    @Inject
    lateinit var modifyNavigator: ModifyNavigator

    override val viewModel: HomeListViewModel by viewModels()

    private val modifyLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            when (it.resultCode) {
                // TODO : 깔쌈하게...
                HomeActivity.RESULT_CODE_CREATE -> {
                    showSnackBar("도시락 기록이 저장되었어요")
                    viewModel.getWeeklyMealList()
                }
                HomeActivity.RESULT_CODE_MODIFY -> {
                    showSnackBar("도시락 기록이 수정되었어요")
                    viewModel.getWeeklyMealList()
                }
                HomeActivity.RESULT_CODE_DELETE -> {
                    showSnackBar("도시락 기록이 삭제되었어요")
                    viewModel.getWeeklyMealList()
                }
            }
        }

    override val backFrame: FrameLayout by lazy {
        binding.frameBack
    }

    override fun initLayout() {
        bind {
            vm = viewModel
            lifecycleOwner = this@HomeListActivity

            list.adapter = HomeListFeedAdapter {
                viewModel.event(HomeListContract.Event.Click.Feed(it))
            }
        }

        viewModel.getWeeklyMealList()
    }

    override fun setupCollect() {
        fun move(effect: HomeListContract.Effect.Move) {
            when (effect) {
                is HomeListContract.Effect.Move.ChangeDate -> {
                    goToChangeDate(effect.nowDate)
                }
                is HomeListContract.Effect.Move.Feed -> {
                    modifyLauncher.launch(modifyNavigator.start(this, effect.id))
                }
            }
        }

        repeatCallDefaultOnStarted {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is HomeListContract.Effect.Move -> {
                        move(effect)
                    }
                }
            }
        }

        repeatCallDefaultOnStarted {
            viewModel.state.collect {
                if (it.isLoading) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }
    }

    private fun goToChangeDate(nowDate: DateTime) {
        HomeListMonthlyDialog
            .newInstance()
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
            duration = Snackbar.LENGTH_SHORT
        )
    }

    companion object {
        const val KEY_NOW_DATE = "now_date"
    }
}