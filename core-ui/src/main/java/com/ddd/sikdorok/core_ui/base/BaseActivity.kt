package com.ddd.sikdorok.core_ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.ddd.sikdorok.core_ui.common.LoadingDialogFragment
import com.ddd.sikdorok.core_ui.util.show

abstract class BaseActivity<T : ViewDataBinding>(private val inflater: (LayoutInflater) -> T) :
    AppCompatActivity() {

    lateinit var binding: T

    protected abstract val viewModel: BaseViewModel

    protected var loadingDialogFragment: LoadingDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflater(layoutInflater)
        setContentView(binding.root)

        initLayout()
        setupCollect()

    }

    protected abstract fun initLayout()

    protected abstract fun setupCollect()

    protected fun bind(action: T.() -> Unit) {
        binding.run(action)
    }

    protected fun showLoading() {
        if (!isFinishing
            && !isDestroyed
            && !supportFragmentManager.isDestroyed
            && !supportFragmentManager.isStateSaved
            && loadingDialogFragment == null
        ) {
            loadingDialogFragment = LoadingDialogFragment()
            loadingDialogFragment?.show(supportFragmentManager)
        }
    }

    protected fun hideLoading() {
        if (!isFinishing
            && !isDestroyed
            && !supportFragmentManager.isDestroyed
            && !supportFragmentManager.isStateSaved
            && loadingDialogFragment != null
        ) {
            loadingDialogFragment?.dismissAllowingStateLoss()
            loadingDialogFragment = null
        }
    }
}
