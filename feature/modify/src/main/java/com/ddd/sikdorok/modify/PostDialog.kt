package com.ddd.sikdorok.modify

import android.Manifest
import android.app.ActionBar.LayoutParams
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.ddd.sikdorok.core_ui.base.BaseDialogFragment
import com.ddd.sikdorok.extensions.dpToPx
import com.ddd.sikdorok.modify.databinding.DialogPostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDialog : BaseDialogFragment<DialogPostBinding>(DialogPostBinding::inflate) {

    override val viewModel: ModifyViewModel by activityViewModels()

    override fun initLayout() {
        binding.viewCamera.setOnClickListener {
            viewModel.event(
                ModifyContract.Event.OnClickPostItem(
                    ModifyContract.Event.OnClickPostItem.Type.CAMERA,
                    requireActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                )
            )
            dismiss()
        }

        binding.viewAlbum.setOnClickListener {
            viewModel.event(
                ModifyContract.Event.OnClickPostItem(
                    ModifyContract.Event.OnClickPostItem.Type.ALBUM,
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        requireActivity().checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
                    } else {
                        requireActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    }
                )
            )
            dismiss()
        }

        binding.viewDefault.setOnClickListener {
            viewModel.event(ModifyContract.Event.OnClickImageForDefault)
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
        fun show(fm: FragmentManager, tag: String) = PostDialog().show(fm, tag)
    }
}
