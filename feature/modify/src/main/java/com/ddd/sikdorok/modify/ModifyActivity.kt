package com.ddd.sikdorok.modify

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ddd.sikdorok.core_ui.base.BackFrameActivity
import com.ddd.sikdorok.core_ui.util.makeAlertDialog
import com.ddd.sikdorok.extensions.compressBitmap
import com.ddd.sikdorok.extensions.convertImageBitmapToByteArray
import com.ddd.sikdorok.extensions.showSnackBar
import com.ddd.sikdorok.extensions.uriToBitmap
import com.ddd.sikdorok.modify.databinding.ActivityModifyBinding
import com.ddd.sikdorok.shared.code.Icon
import com.ddd.sikdorok.shared.code.Tag
import com.ddd.sikdorok.shared.code.album
import com.ddd.sikdorok.shared.code.camera
import com.ddd.sikdorok.shared.date.DATE_PATTERNS
import com.ddd.sikdorok.shared.date.TIME_PATTERNS
import com.ddd.sikdorok.shared.key.Keys
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.joda.time.DateTime
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat

// TODO : 데바로 변경
@AndroidEntryPoint
class ModifyActivity : BackFrameActivity<ActivityModifyBinding>(ActivityModifyBinding::inflate) {

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val bitmap = result.data?.extras?.getParcelable<Bitmap>("data")
                ?: return@registerForActivityResult
            val data = Bitmap.createScaledBitmap(bitmap, bitmap.width, bitmap.height, false).copy(
                Bitmap.Config.ARGB_8888, true
            )
            viewModel.event(
                ModifyContract.Event.OnUpdateImage(
                    compressBitmap(Bitmap.CompressFormat.PNG, data, 100) ?: Uri.EMPTY
                )
            )
        }

    private val pickMultipleMedia = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->

        if (uri != null) {
            viewModel.event(
                ModifyContract.Event.OnUpdateImage(
                    uri
                )
            )
        }
    }

    override val backFrame: FrameLayout by lazy { binding.frameBack }

    override val viewModel: ModifyViewModel by viewModels()

    override fun initLayout() {

        bind {
            vm = viewModel
            lifecycleOwner = this@ModifyActivity
        }

        window.statusBarColor = getColor(com.ddd.sikdorok.core_design.R.color.white)

        binding.fabCamera.setOnClickListener {
            viewModel.event(ModifyContract.Event.OnClickCameraFAB)
        }
        binding.rlDate.setOnClickListener {
            viewModel.event(ModifyContract.Event.OnClickDate(binding.tvDate.text.toString()))
        }
        binding.rlTime.setOnClickListener {
            viewModel.event(ModifyContract.Event.OnClickTime(binding.tvTime.text.toString()))
        }
        binding.tvSave.setOnClickListener {
            savePost()
        }
        binding.ivInfo.setOnClickListener {

        }
        binding.checkMainPost.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.event(ModifyContract.Event.OnClickCheck(isChecked))
        }

        binding.radioTag.setOnCheckedChangeListener { radioGroup, i ->
            getTagByViewId(i)?.code?.let {
                viewModel.event(ModifyContract.Event.OnClickDay(it))
            }
        }

        binding.radioMealType.setOnCheckedChangeListener { radioGroup, i ->
            getIconByViewId(i)?.code?.let {
                viewModel.event(ModifyContract.Event.OnClickIcon(it))
            }
        }

        viewModel.getFeedInfo()
    }

    override fun onClickBackFrameIcon() {
        viewModel.event(ModifyContract.Event.OnClickBackIcon)
    }

    override fun setupCollect() {
        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { state ->
                try {
                    val now =
                        DateTime.parse(state.time, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"))
                    binding.tvDate.text = now.toString(DATE_PATTERNS)
                    binding.tvTime.text = now.toString(TIME_PATTERNS)
                } catch (_: Exception) {

                }

                when {
                    !state.imageUrl.isNullOrEmpty() -> {
                        Glide.with(this)
                            .load(state.imageUrl)
                            .into(binding.ivMain)
                    }
                    (state.image != Uri.EMPTY) && state.image != null -> {
                        Glide.with(this)
                            .load(state.image)
                            .centerCrop()
                            .into(binding.ivMain)
                    }
                }

                binding.editInput.setText(state.memo)

                binding.ivDefaultAdd.isVisible = state.image == Uri.EMPTY
                binding.checkMainPost.isChecked = state.isMainPost

                getViewIdByIcon(state.tag)?.let { binding.radioTag.check(it) }
                getViewIdByTag(state.icon)?.let { binding.radioMealType.check(it) }
            }
            .launchIn(lifecycleScope)

        viewModel.effect
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { sideEffect ->
                when (sideEffect) {
                    ModifyContract.SideEffect.OnFinishCreate -> {
                        setResult(RESULT_CODE_CREATE)
                        finish()
                    }
                    ModifyContract.SideEffect.OnFinishModify -> {
                        setResult(RESULT_CODE_MODIFY)
                        finish()
                    }
                    ModifyContract.SideEffect.OnFinishDelete -> {
                        setResult(RESULT_CODE_DELETE)
                        finish()
                    }
                    ModifyContract.SideEffect.OnFinish -> {
                        finish()
                    }
                    ModifyContract.SideEffect.ShowPostDialog -> {
                        PostDialog.show(supportFragmentManager, "")
                    }
                    ModifyContract.SideEffect.RequestCamera -> {
                        requestPermissions(
                            listOf(Manifest.permission.CAMERA).toTypedArray(),
                            camera
                        )
                    }
                    ModifyContract.SideEffect.RequestAlbum -> {
                        val permissions =
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                Manifest.permission.READ_MEDIA_IMAGES
                            } else {
                                Manifest.permission.READ_EXTERNAL_STORAGE
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            }

                        requestPermissions(
                            listOf(permissions).toTypedArray(),
                            album
                        )
                    }
                    ModifyContract.SideEffect.NaviToCamera -> {
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        cameraLauncher.launch(intent)
                    }
                    ModifyContract.SideEffect.NaviToAlbum -> {
                        pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                    is ModifyContract.SideEffect.ShowDatePicker -> {
                        val date = DateTime.parse(
                            sideEffect.date,
                            DateTimeFormat.forPattern(DATE_PATTERNS)
                        )

                        ModifyDateTimePicker.getInstance(date.toString())
                            .show(supportFragmentManager, Keys.MODIFY_DATE_FRAGMENT)
                    }

                    is ModifyContract.SideEffect.ShowTimePicker -> {
                        val time = DateTime.parse(
                            sideEffect.time,
                            DateTimeFormat.forPattern(TIME_PATTERNS)
                        )

                        ModifyTimePicker.getInstance(time.toString())
                            .show(supportFragmentManager, Keys.MODIFY_TIME_FRAGMENT)
                    }
                    ModifyContract.SideEffect.OpenMenu -> {
                        registerForContextMenu(binding.more)
                        binding.more.showContextMenu()
                    }
                    is ModifyContract.SideEffect.Fail -> {
                        showSnackBar(
                            view = binding.root,
                            message = sideEffect.errorMsg,
                            backgroundColor = com.ddd.sikdorok.core_design.R.color.text_color,
                            textColor = com.ddd.sikdorok.core_design.R.color.white,
                            duration = Snackbar.LENGTH_LONG
                        )
                    }
                    else -> Unit
                }
            }
            .launchIn(lifecycleScope)

        pickerDialogResult()
    }

    private fun pickerDialogResult() {
        supportFragmentManager.setFragmentResultListener(
            Keys.MODIFY_DATE_FRAGMENT,
            this
        ) { _, bundle ->
            val data = bundle.getSerializable(Keys.MODIFY_DATE_RESULT) as PickerData.Date

            binding.tvDate.text =
                DateTime.parse("${data.year}-${data.month}-${data.day}").toString(DATE_PATTERNS)
        }

        supportFragmentManager.setFragmentResultListener(
            Keys.MODIFY_TIME_FRAGMENT,
            this
        ) { _, bundle ->
            val data = bundle.getSerializable(Keys.MODIFY_TIME_RESULT) as PickerData.Time

            binding.tvTime.text =
                LocalTime.parse("${data.hours}:${data.minute}").toString(TIME_PATTERNS)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            camera -> {
                val result = grantResults.first()
                if (result == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivity(intent)
                }
            }

            album -> {
                val result = grantResults.first()
                if (result == PackageManager.PERMISSION_GRANTED) {
                    pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            }
        }
    }

    @IdRes
    private fun getViewIdByTag(iconCode: String): Int? {
        return when (iconCode) {
            Icon.RICE.code -> R.id.type_button_rice
            Icon.SALAD.code -> R.id.type_button_salad
            Icon.NOODLE.code -> R.id.type_button_noodle
            Icon.MEAT.code -> R.id.type_button_meat
            Icon.BREAD.code -> R.id.type_button_bread
            Icon.FAST_FOOD.code -> R.id.type_button_burger
            Icon.SUSHI.code -> R.id.type_button_sushi
            Icon.CAKE.code -> R.id.type_button_snack
            Icon.NOTHING.code -> R.id.type_button_nothing
            else -> null
        }
    }

    @IdRes
    private fun getViewIdByIcon(tagCode: String?): Int? {
        return when (tagCode) {
            Tag.MORNING.code -> R.id.tag_button_morning
            Tag.LUNCH.code -> R.id.tag_button_lunch
            Tag.DINNER.code -> R.id.tag_button_evening
            Tag.SNACK.code -> R.id.tag_button_snack
            else -> null
        }
    }

    private fun getIconByViewId(@IdRes viewId: Int): Icon? {
        return when (viewId) {
            R.id.type_button_rice -> Icon.RICE
            R.id.type_button_salad -> Icon.SALAD
            R.id.type_button_noodle -> Icon.NOODLE
            R.id.type_button_meat -> Icon.MEAT
            R.id.type_button_bread -> Icon.BREAD
            R.id.type_button_burger -> Icon.FAST_FOOD
            R.id.type_button_sushi -> Icon.SUSHI
            R.id.type_button_snack -> Icon.CAKE
            R.id.type_button_nothing -> Icon.NOTHING
            else -> null
        }
    }


    private fun getTagByViewId(@IdRes viewId: Int): Tag? {
        return when (viewId) {
            R.id.tag_button_morning -> Tag.MORNING
            R.id.tag_button_lunch -> Tag.LUNCH
            R.id.tag_button_evening -> Tag.DINNER
            R.id.tag_button_snack -> Tag.SNACK
            else -> null
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.menu, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_save -> {
                savePost()
            }
            R.id.menu_item_delete -> {
                makeAlertDialog(
                    title = "저장하신 도시락 기록을 삭제하시겠어요?",
                    confirmText = "확인",
                    cancelText = "취소",
                    onConfirm = {
                        viewModel.onClickDelete()
                    }
                )
            }
            R.id.menu_item_share -> {
                viewModel.onClickShare()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun savePost() {
        val bitmap = uriToBitmap(this, viewModel.state.value.image)
        val byteArray = convertImageBitmapToByteArray(bitmap)
        viewModel.event(
            ModifyContract.Event.OnSavedFeed(
                file = byteArray,
                tag = viewModel.state.value.tag,
                time = viewModel.state.value.time,
                memo = binding.editInput.text.toString(),
                icon = viewModel.state.value.icon,
                isMainFeed = viewModel.state.value.isMainPost
            )
        )
    }

    private fun showTooltip() {

    }

    companion object {
        const val RESULT_CODE_CREATE = 3000
        const val RESULT_CODE_MODIFY = 3001
        const val RESULT_CODE_DELETE = 3002
    }
}
