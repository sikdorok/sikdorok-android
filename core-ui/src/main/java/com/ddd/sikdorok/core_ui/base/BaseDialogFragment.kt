package com.ddd.sikdorok.core_ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.ddd.sikdorok.core_ui.common.LoadingDialogFragment
import com.ddd.sikdorok.core_ui.util.show

abstract class BaseDialogFragment<T : ViewDataBinding>(private val inflater: (LayoutInflater) -> T) :
    DialogFragment() {

    lateinit var binding: T

    protected abstract val viewModel: BaseViewModel

    protected var loadingDialogFragment: LoadingDialogFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflater(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        setupCollect()
    }

    protected abstract fun initLayout()

    protected open fun setupCollect() = Unit

    protected fun bind(action: T.() -> Unit) {
        binding.run(action)
    }

    protected fun BaseDialogFragment<T>.setRoundedDialog(@DrawableRes drawableRes: Int) {
        dialog?.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                drawableRes
            )
        )
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
    }

    protected fun showLoading() {
        if (activity?.isFinishing == false
            && activity?.isDestroyed == false
            && !parentFragmentManager.isDestroyed
            && !parentFragmentManager.isStateSaved
            && loadingDialogFragment == null
        ) {
            loadingDialogFragment = LoadingDialogFragment()
            loadingDialogFragment?.show(parentFragmentManager)
        }
    }

    protected fun hideLoading() {
        if (activity?.isFinishing == false
            && activity?.isDestroyed == false
            && loadingDialogFragment?.parentFragmentManager?.isDestroyed == false
            && loadingDialogFragment?.parentFragmentManager?.isStateSaved == false
            && loadingDialogFragment != null
        ) {
            loadingDialogFragment?.dismissAllowingStateLoss()
            loadingDialogFragment = null
        }
    }
}
