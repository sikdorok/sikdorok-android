package com.ddd.sikdorok.home.dialog

import android.view.Gravity
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ddd.sikdorok.core_ui.base.BaseDialogFragment
import com.ddd.sikdorok.core_ui.util.repeatCallDefaultOnStarted
import com.ddd.sikdorok.extensions.dpToPx
import com.ddd.sikdorok.home.HomeViewModel
import com.ddd.sikdorok.home.R
import com.ddd.sikdorok.home.databinding.DialogHomeMonthlyBinding
import com.ddd.sikdorok.home.date.HomeDateAdapter
import com.ddd.sikdorok.home.month.HomeMonthAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeMonthlyDialog :
    BaseDialogFragment<DialogHomeMonthlyBinding>(DialogHomeMonthlyBinding::inflate) {

    private val activityViewModel: HomeViewModel by activityViewModels()

    override val viewModel: HomeMonthlyViewModel by viewModels()

    override fun getTheme(): Int = R.style.WeeklyDialog

    override fun initLayout() {
        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            listWeekly.adapter = HomeDateAdapter {
                viewModel.event(HomeMonthlyContract.Event.ClickWeeklyDate(it))
            }

            listMonth.adapter = HomeMonthAdapter {
                viewModel.onClickMonth(it)
            }
        }

        setupDialogView()
    }

    override fun setupCollect() {
        repeatCallDefaultOnStarted {
            viewModel.effect.collect { effect ->
                when (effect) {
                    HomeMonthlyContract.Effect.Close -> {
                        dismissAllowingStateLoss()
                    }
                    is HomeMonthlyContract.Effect.GoMainFeed -> {
                        activityViewModel.getWeeklyMealboxInfo(effect.date)
                        dismissAllowingStateLoss()
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

    private fun setupDialogView() {
        dialog?.window?.run {
            setBackgroundDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_home_dialog)
            )
            setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setGravity(Gravity.BOTTOM)
            attributes = dialog?.window?.attributes?.apply {
                y = 20.dpToPx
            }
        }
    }

    companion object {
        fun newInstance(): HomeMonthlyDialog {
            return HomeMonthlyDialog()
        }
    }
}
