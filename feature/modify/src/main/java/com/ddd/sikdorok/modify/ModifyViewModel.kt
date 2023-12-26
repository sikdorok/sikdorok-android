package com.ddd.sikdorok.modify

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddd.sikdorok.core_ui.base.BaseContract
import com.ddd.sikdorok.core_ui.base.BaseViewModel
import com.ddd.sikdorok.domain.modify.CreateFeedUseCase
import com.ddd.sikdorok.domain.modify.DeleteFeedUseCase
import com.ddd.sikdorok.domain.modify.ReadFeedUseCase
import com.ddd.sikdorok.domain.modify.UpdateFeedUseCase
import com.ddd.sikdorok.shared.code.Icon
import com.ddd.sikdorok.shared.code.Tag
import com.ddd.sikdorok.shared.modify.FeedRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyViewModel @Inject constructor(
    private val createFeedUseCase: CreateFeedUseCase,
    private val readFeedUseCase: ReadFeedUseCase,
    private val modifyFeedUseCase: UpdateFeedUseCase,
    private val deleteFeedUseCase: DeleteFeedUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel(),
    BaseContract<ModifyContract.State, ModifyContract.Event, ModifyContract.SideEffect> {

    private val _effect = MutableSharedFlow<ModifyContract.SideEffect>()
    override val effect: SharedFlow<ModifyContract.SideEffect>
        get() = _effect.asSharedFlow()

    private val _state = MutableStateFlow(ModifyContract.State())
    override val state: StateFlow<ModifyContract.State>
        get() = _state.asStateFlow()

    var savedImageToken: ArrayList<String> = arrayListOf()

    val postId: String by lazy {
        savedStateHandle.get<String>(KEY_POST_ID) ?: ""
    }

    val postDate: String by lazy {
        savedStateHandle.get<String>(KEY_POST_DATE).orEmpty()
    }

    override fun event(event: ModifyContract.Event) {
        viewModelScope.launch {
            when (event) {
                ModifyContract.Event.OnClickBackIcon -> {
                    _effect.emit(ModifyContract.SideEffect.OnFinish)
                }
                ModifyContract.Event.OnClickCameraFAB -> {
                    _effect.emit(ModifyContract.SideEffect.ShowPostDialog)
                }
                is ModifyContract.Event.OnClickCheck -> {
                    _state.update {
                        it.copy(
                            isMainPost = event.isChecked
                        )
                    }
                }
                is ModifyContract.Event.OnClickPostItem -> {
                    val type = event.type
                    when (type) {
                        ModifyContract.Event.OnClickPostItem.Type.CAMERA -> {
                            if (event.isGrant) {
                                _effect.emit(ModifyContract.SideEffect.NaviToCamera)
                            } else {
                                _effect.emit(ModifyContract.SideEffect.RequestCamera)
                            }
                        }
                        ModifyContract.Event.OnClickPostItem.Type.ALBUM -> {
                            if (event.isGrant) {
                                _effect.emit(ModifyContract.SideEffect.NaviToAlbum)
                            } else {
                                _effect.emit(ModifyContract.SideEffect.RequestAlbum)
                            }
                        }
                    }
                }
                is ModifyContract.Event.OnUpdateImage -> {
                    _state.update { it.copy(image = event.uri) }
                }
                is ModifyContract.Event.OnClickTime -> {
                    _effect.emit(ModifyContract.SideEffect.ShowTimePicker(event.time))
                }
                is ModifyContract.Event.OnClickDate -> {
                    _effect.emit(ModifyContract.SideEffect.ShowDatePicker(event.date))
                }
                is ModifyContract.Event.OnClickIcon -> {
                    _state.update { it.copy(icon = event.code) }
                }
                is ModifyContract.Event.OnClickDay -> {
                    _state.update { it.copy(tag = event.code) }
                }
                is ModifyContract.Event.OnSavedFeed -> {
                    if (postId.isEmpty()) {
                        createFeedUseCase.invoke(
                            event.file,
                            FeedRequest(
                                tag = Tag.findTag(state.value.tag),
                                time = event.time,
                                memo = event.memo,
                                icon = Icon.findIcon(state.value.icon),
                                isMain = event.isMainFeed,
                                deletePhotoTokens = if (event.file.isEmpty()) {
                                    emptyList()
                                } else {
                                    savedImageToken
                                }
                            )
                        ).apply {
                            if (code == 200) {
                                _effect.emit(ModifyContract.SideEffect.OnFinishCreate)
                            } else {
                                _effect.emit(ModifyContract.SideEffect.Fail(this.message))
                            }
                        }
                    } else {
                        modifyFeedUseCase.invoke(
                            event.file,
                            FeedRequest(
                                feedId = postId,
                                tag = Tag.findTag(state.value.tag),
                                time = event.time,
                                memo = event.memo,
                                icon = Icon.findIcon(state.value.icon),
                                isMain = event.isMainFeed,
                                deletePhotoTokens = if (event.file.isEmpty()) {
                                    emptyList()
                                } else {
                                    savedImageToken
                                }
                            )
                        ).apply {
                            if (code == 200) {
                                _effect.emit(ModifyContract.SideEffect.OnFinishModify)
                            } else {
                                _effect.emit(ModifyContract.SideEffect.Fail(this.message))
                            }
                        }
                    }
                }
                ModifyContract.Event.OnClickMore -> {
                    _effect.emit(ModifyContract.SideEffect.OpenMenu)
                }
                ModifyContract.Event.OnClickDelete -> {
                    deleteFeedUseCase(postId).apply {
                        if(code == 200) {
                            _effect.emit(ModifyContract.SideEffect.OnFinishDelete)
                        } else {
                            _effect.emit(ModifyContract.SideEffect.Fail(this.message))
                        }
                    }
                }
                else -> Unit
            }
        }
    }

    fun getFeedInfo() {
        if (postId.isNotEmpty()) {
            viewModelScope.launch {
                readFeedUseCase(postId).data?.let { response ->
                    savedImageToken.addAll(
                        response.feedInfo.photosInfoList?.map { it.token.orEmpty() }
                            ?: arrayListOf())

                    _state.update {
                        it.copy(
                            imageUrl = response.feedInfo.photosInfoList?.firstOrNull()?.uploadFullPath,
                            icon = response.feedInfo.icon,
                            tag = response.feedInfo.tag,
                            memo = response.feedInfo.memo ?: "",
                            isMainPost = response.feedInfo.isMain,
                            time = response.feedInfo.time,
                            id = response.feedInfo.feedId
                        )
                    }
                }
            }
        } else {
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        time = postDate
                    )
                }
            }
        }
    }

    fun onClickMore() {
        event(ModifyContract.Event.OnClickMore)
    }

    fun onClickDelete() {
        event(ModifyContract.Event.OnClickDelete)
    }

    fun onClickShare() {
        event(ModifyContract.Event.OnClickShare)
    }

    companion object {
        const val KEY_POST_ID = "post_id"
        const val KEY_POST_DATE = "post_date"
    }
}
