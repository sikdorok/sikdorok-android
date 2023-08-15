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

abstract class BaseDialogFragment<T : ViewDataBinding>(private val inflater: (LayoutInflater) -> T) : DialogFragment() {

    lateinit var binding: T

    protected abstract val viewModel: BaseViewModel

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
        dialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), drawableRes))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
    }

}
