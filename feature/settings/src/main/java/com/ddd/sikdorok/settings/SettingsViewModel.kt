package com.ddd.sikdorok.settings

import androidx.lifecycle.viewModelScope
import com.ddd.sikdorok.core_ui.base.BaseContract
import com.ddd.sikdorok.core_ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : BaseViewModel(), BaseContract<SettingsContract.State, SettingsContract.Event, SettingsContract.SideEffect> {

    private val _state = MutableStateFlow(SettingsContract.State())
    override val state: StateFlow<SettingsContract.State>
        get() = _state.asStateFlow()

    private val _effect = MutableSharedFlow<SettingsContract.SideEffect>()
    override val effect: SharedFlow<SettingsContract.SideEffect>
        get() = _effect.asSharedFlow()

    override fun event(event: SettingsContract.Event) {
        viewModelScope.launch {
            when(event) {
                SettingsContract.Event.OnClickPolicy -> {
                    _effect.emit(SettingsContract.SideEffect.NaviToPolicy)
                }
                SettingsContract.Event.OnClickAccountDelete -> {
                    _effect.emit(SettingsContract.SideEffect.NaviToDeleteAccount)
                }
                SettingsContract.Event.OnClickProfileManage -> {
                    _effect.emit(SettingsContract.SideEffect.NaviToProfileManage)
                }
            }
        }
    }

}
