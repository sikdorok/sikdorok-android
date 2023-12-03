package com.ddd.sikdorok.home.list

import android.app.Activity
import android.widget.FrameLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import com.ddd.sikdorok.core_ui.base.BackFrameActivity
import com.ddd.sikdorok.core_ui.util.repeatCallDefaultOnStarted
import com.ddd.sikdorok.core_ui.util.show
import com.ddd.sikdorok.home.databinding.ActivityHomeListBinding
import com.ddd.sikdorok.home.list.dialog.HomeListMonthlyDialog
import com.ddd.sikdorok.home.list.feed.HomeListFeedAdapter
import com.ddd.sikdorok.navigator.modify.ModifyNavigator
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
            if (it.resultCode == Activity.RESULT_OK) {
                viewModel.getWeeklyMealList()
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


    companion object {
        const val KEY_NOW_DATE = "now_date"
    }
}