package com.ddd.sikdorok.modify

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.ContextMenu
import android.view.Gravity
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ddd.sikdorok.core_ui.base.BackFrameActivity
import com.ddd.sikdorok.core_ui.util.makeAlertDialog
import com.ddd.sikdorok.extensions.compressBitmap
import com.ddd.sikdorok.extensions.convertImageBitmapToByteArray
import com.ddd.sikdorok.extensions.repeatCallDefaultOnStarted
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
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

// TODO : 데바로 변경 언제해...
@AndroidEntryPoint
class ModifyActivity : BackFrameActivity<ActivityModifyBinding>(ActivityModifyBinding::inflate) {

    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { result ->
            result?.let {
                val data =
                    Bitmap.createScaledBitmap(
                        result,
                        binding.ivMain.width,
                        binding.ivMain.height,
                        true
                    )
                        .copy(Bitmap.Config.ARGB_8888, true)

                viewModel.event(
                    ModifyContract.Event.OnUpdateImage(
                        compressBitmap(Bitmap.CompressFormat.JPEG, data, 100) ?: Uri.EMPTY
                    )
                )
            }
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
            showLoading()
            lifecycleScope.launch {
                delay(100)
                savePost()
            }
        }
        binding.ivInfo.setOnClickListener {
            showTooltip()
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

        showLoading()
        viewModel.getFeedInfo()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val focusView = currentFocus
        if (focusView != null) {
            val rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = ev.x.toInt()
            val y = ev.y.toInt()
            if (!rect.contains(x, y)) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(focusView.windowToken, 0)
                focusView.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onClickBackFrameIcon() {
        viewModel.event(ModifyContract.Event.OnClickBackIcon)
    }

    override fun setupCollect() {
        repeatCallDefaultOnStarted {
            viewModel.state.collect { state ->
                hideLoading()

                try {
                    val now =
                        DateTime.parse(state.time, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"))
                    binding.tvDate.text = now.toString(DATE_PATTERNS)
                    binding.tvTime.text = now.toString(TIME_PATTERNS)
                } catch (_: Exception) {

                }

                when {
                    state.imageRefresh == true -> {
                        Glide.with(this@ModifyActivity)
                            .clear(binding.ivMain)
                    }
                    !state.imageUrl.isNullOrEmpty() -> {
                        Glide.with(this@ModifyActivity)
                            .load(state.imageUrl)
                            .into(binding.ivMain)
                    }
                    (state.image != Uri.EMPTY) && state.image != null -> {
                        Glide.with(this@ModifyActivity)
                            .load(state.image)
                            .into(binding.ivMain)
                    }
                }

                // 기본 이미지 규칙 -
                binding.ivDefaultAdd.isVisible =
                    (state.image == null) && (state.imageUrl.isNullOrEmpty())
                binding.checkMainPost.isChecked = state.isMainPost

                getViewIdByIcon(state.tag)?.let { binding.radioTag.check(it) }
                getViewIdByTag(state.icon)?.let { binding.radioMealType.check(it) }
            }
        }

        repeatCallDefaultOnStarted {
            viewModel.effect.collect { sideEffect ->
                when (sideEffect) {
                    ModifyContract.SideEffect.OnFinishCreate -> {
                        hideLoading()
                        setResult(RESULT_CODE_CREATE)
                        finish()
                    }
                    ModifyContract.SideEffect.OnFinishModify -> {
                        hideLoading()
                        setResult(RESULT_CODE_MODIFY)
                        finish()
                    }
                    ModifyContract.SideEffect.OnFinishDelete -> {
                        hideLoading()
                        setResult(RESULT_CODE_DELETE)
                        finish()
                    }
                    ModifyContract.SideEffect.OnFinish -> {
                        finish()
                    }
                    ModifyContract.SideEffect.ShowPostDialog -> {
                        PostDialog.newInstance()
                            .apply {
                                onConfirmCamera = {
                                    viewModel.event(
                                        ModifyContract.Event.OnClickPostItem(
                                            ModifyContract.Event.OnClickPostItem.Type.CAMERA,
                                            checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                                        )
                                    )
                                }
                                onConfirmPhoto = {
                                    viewModel.event(
                                        ModifyContract.Event.OnClickPostItem(
                                            ModifyContract.Event.OnClickPostItem.Type.ALBUM,
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                                checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
                                            } else {
                                                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                                            }
                                        )
                                    )
                                }
                                onConfirmDefault = {
                                    viewModel.event(ModifyContract.Event.OnClickImageForDefault)
                                }
                            }
                            .show(supportFragmentManager, "")
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
                        takePictureLauncher.launch(null)
                    }
                    ModifyContract.SideEffect.NaviToAlbum -> {
                        pickMultipleMedia.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
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
                        hideLoading()

                        showSnackBar(
                            view = binding.root,
                            message = sideEffect.errorMsg,
                            backgroundColor = com.ddd.sikdorok.core_design.R.color.text_color,
                            textColor = com.ddd.sikdorok.core_design.R.color.white,
                            duration = Snackbar.LENGTH_LONG
                        )
                    }
                    is ModifyContract.SideEffect.ShowSnackBar -> {
                        hideLoading()

                        showSnackBar(
                            view = binding.root,
                            message = sideEffect.text,
                            backgroundColor = com.ddd.sikdorok.core_design.R.color.text_color,
                            textColor = com.ddd.sikdorok.core_design.R.color.white,
                            duration = Snackbar.LENGTH_LONG
                        )
                    }
                    ModifyContract.SideEffect.SuccessLoadData -> {
                        hideLoading()
                        binding.editInput.setText(viewModel.memo)
                    }
                }
            }
        }

        pickerDialogResult()
    }

    private fun pickerDialogResult() {
        supportFragmentManager.setFragmentResultListener(
            Keys.MODIFY_DATE_FRAGMENT,
            this
        ) { _, bundle ->
            val data = bundle.getSerializable(Keys.MODIFY_DATE_RESULT) as PickerData.Date

            val time = DateTime.parse("${data.year}-${data.month}-${data.day}")
                .toString("yyyy-MM-dd HH:mm:ss")

            viewModel.event(ModifyContract.Event.OnFinishDatePicker(time))
        }

        supportFragmentManager.setFragmentResultListener(
            Keys.MODIFY_TIME_FRAGMENT,
            this
        ) { _, bundle ->
            val data = bundle.getSerializable(Keys.MODIFY_TIME_RESULT) as PickerData.Time

            val timeData = viewModel.state.value.time
            val time = DateTime.parse(timeData, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"))

            val newTime = DateTime.parse(
                "${time.year}-${time.monthOfYear}-${time.dayOfMonth} ${data.hours}:${data.minute}:00",
                DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
            )

            viewModel.event(ModifyContract.Event.OnFinishTimePicker(newTime.toString("yyyy-MM-dd HH:mm:ss")))
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
                showLoading()
                savePost()
            }
            R.id.menu_item_delete -> {
                makeAlertDialog(
                    title = "저장하신 도시락 기록을 삭제하시겠어요?",
                    confirmText = "확인",
                    cancelText = "취소",
                    onConfirm = {
                        showLoading()
                        viewModel.onClickDelete()
                    }
                )
            }
//            R.id.menu_item_share -> {
//                viewModel.onClickShare()
//            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun savePost() {
        val bitmap = uriToBitmap(this, viewModel.state.value.image)
        val byteArray = convertImageBitmapToByteArray(bitmap)

        viewModel.event(
            ModifyContract.Event.OnSavedFeed(
                file = byteArray.takeIf {
                    viewModel.state.value.image != null
                            || !viewModel.state.value.imageUrl.isNullOrEmpty()
                },
                tag = viewModel.state.value.tag,
                time = viewModel.state.value.time,
                memo = binding.editInput.text.toString(),
                icon = viewModel.state.value.icon,
                isMainFeed = viewModel.state.value.isMainPost
            )
        )
    }

    private fun showTooltip() {
        val balloon = Balloon.Builder(this)
            .setWidth(BalloonSizeSpec.WRAP)
            .setHeight(BalloonSizeSpec.WRAP)
            .setTextGravity(Gravity.LEFT)
            .setText("대표 아이콘을 설정해\n달력에 대표 아이콘을 표시해보세요.")
            .setTextColorResource(com.ddd.sikdorok.core_design.R.color.white)
            .setTextSize(15f)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_BALLOON)
            .setArrowSize(10)
            .setArrowPosition(0.1f)
            .setPadding(12)
            .setCornerRadius(8f)
            .setTextTypeface(
                ResourcesCompat.getFont(
                    this,
                    com.ddd.sikdorok.core_design.R.font.leeseoyun
                )!!
            )
            .setBackgroundColorResource(com.ddd.sikdorok.core_design.R.color.text_color)
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
            .setLifecycleOwner(this)
            .build()

        balloon.showAlignTop(binding.ivInfo, 212, -5)
    }

    companion object {
        const val RESULT_CODE_CREATE = 3000
        const val RESULT_CODE_MODIFY = 3001
        const val RESULT_CODE_DELETE = 3002
    }
}
