package com.ddd.sikdorok.core_ui.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.viewModels
import com.ddd.sikdorok.core_ui.base.BaseDialogFragment
import com.ddd.sikdorok.core_ui.databinding.DialogLoadingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadingDialogFragment : BaseDialogFragment<DialogLoadingBinding>(DialogLoadingBinding::inflate) {

    override val viewModel: LoadingDialogViewModel by viewModels()

    override fun initLayout() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
    }
}