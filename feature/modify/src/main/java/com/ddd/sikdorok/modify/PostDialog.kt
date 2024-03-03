package com.ddd.sikdorok.modify

import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ddd.sikdorok.core_ui.base.BaseDialogFragment
import com.ddd.sikdorok.extensions.dpToPx
import com.ddd.sikdorok.modify.databinding.DialogPostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDialog : BaseDialogFragment<DialogPostBinding>(DialogPostBinding::inflate) {

    override val viewModel: ModifyViewModel by activityViewModels()

    var onConfirmCamera: (() -> Unit)? = null

    var onConfirmPhoto: (() -> Unit)? = null

    var onConfirmDefault: (() -> Unit)? = null


    override fun initLayout() {
        binding.viewCamera.setOnClickListener {
            onConfirmCamera?.invoke()
            dismiss()
        }

        binding.viewAlbum.setOnClickListener {
            onConfirmPhoto?.invoke()
            dismiss()
        }

        binding.viewDefault.setOnClickListener {
            onConfirmDefault?.invoke()
            dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setRoundedDialog(com.ddd.sikdorok.core_design.R.drawable.rounded_dialog_background)
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setLayout(272.dpToPx, LayoutParams.WRAP_CONTENT)
    }

    companion object {
        fun newInstance(): PostDialog {
            return PostDialog()
        }
    }
}
