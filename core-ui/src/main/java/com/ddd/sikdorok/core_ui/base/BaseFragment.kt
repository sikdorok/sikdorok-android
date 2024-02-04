package com.ddd.sikdorok.core_ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.ddd.sikdorok.core_ui.common.LoadingDialogFragment
import com.ddd.sikdorok.core_ui.util.show

abstract class BaseFragment<T : ViewDataBinding>(private val inflater: (LayoutInflater) -> T) :
    Fragment() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        setupCollect()
    }

    protected abstract fun initLayout()

    protected open fun setupCollect() = Unit

    protected fun bind(action: T.() -> Unit) {
        binding.run(action)
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
