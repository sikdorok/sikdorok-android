package com.ddd.sikdorok.modify

import androidx.lifecycle.viewModelScope
import com.ddd.sikdorok.core_ui.base.BaseContract
import com.ddd.sikdorok.core_ui.base.BaseViewModel
import com.ddd.sikdorok.domain.modify.CreateFeedUseCase
import com.ddd.sikdorok.domain.modify.DeleteFeedUseCase
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
    private val modifyFeedUseCase: UpdateFeedUseCase,
    private val deleteFeedUseCase: DeleteFeedUseCase
) : BaseViewModel(), BaseContract<ModifyContract.State, ModifyContract.Event, ModifyContract.SideEffect> {

    private val _effect = MutableSharedFlow<ModifyContract.SideEffect>()
    override val effect: SharedFlow<ModifyContract.SideEffect>
        get() = _effect.asSharedFlow()

    private val _state = MutableStateFlow(ModifyContract.State())
    override val state: StateFlow<ModifyContract.State>
        get() = _state.asStateFlow()

    override fun event(event: ModifyContract.Event) {
        viewModelScope.launch {
            when(event) {
                ModifyContract.Event.OnClickBackIcon -> {
                    _effect.emit(ModifyContract.SideEffect.OnFinishModify)
                }
                ModifyContract.Event.OnClickCameraFAB -> {
                    _effect.emit(ModifyContract.SideEffect.ShowPostDialog)
                }
                is ModifyContract.Event.OnClickPostItem -> {
                   val type = event.type
                   when(type) {
                       ModifyContract.Event.OnClickPostItem.Type.CAMERA -> {
                           if(event.isGrant) {
                               _effect.emit(ModifyContract.SideEffect.NaviToCamera)
                           } else {
                               _effect.emit(ModifyContract.SideEffect.RequestCamera)
                           }
                       }
                       ModifyContract.Event.OnClickPostItem.Type.ALBUM -> {
                           if(event.isGrant) {
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
                    viewModelScope.launch {
                        createFeedUseCase.invoke(
                            event.fileName,
                            FeedRequest(
                                Tag.valueOf(event.tag),
                                event.time,
                                event.memo,
                                Icon.valueOf(event.icon),
                                event.isMainFeed,
                                emptyList()
                            )
                        )
                    }
                }
                else -> Unit
            }
        }
    }
}
