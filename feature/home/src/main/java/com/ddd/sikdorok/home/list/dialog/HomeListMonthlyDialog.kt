package com.ddd.sikdorok.home.list.dialog

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ddd.sikdorok.core_ui.base.BaseDialogFragment
import com.ddd.sikdorok.core_ui.util.repeatCallDefaultOnStarted
import com.ddd.sikdorok.home.R
import com.ddd.sikdorok.home.databinding.DialogHomeListMonthlyBinding
import com.ddd.sikdorok.home.list.HomeListViewModel
import com.ddd.sikdorok.home.month.HomeMonthAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeListMonthlyDialog :
    BaseDialogFragment<DialogHomeListMonthlyBinding>(DialogHomeListMonthlyBinding::inflate) {

    private val activityViewModel: HomeListViewModel by activityViewModels()

    override val viewModel: HomeListMonthlyViewModel by viewModels()

    override fun getTheme(): Int = R.style.WeeklyDialog

    override fun initLayout() {
        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

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
                    HomeListMonthlyContract.Effect.Close -> {
                        dismissAllowingStateLoss()
                    }
                    is HomeListMonthlyContract.Effect.ChangeMonth -> {
                        activityViewModel.getWeeklyMealList(effect.date)
                        dismissAllowingStateLoss()
                    }
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
        }
    }

    companion object {
        fun newInstance(): HomeListMonthlyDialog {
            return HomeListMonthlyDialog()
        }
    }
}
