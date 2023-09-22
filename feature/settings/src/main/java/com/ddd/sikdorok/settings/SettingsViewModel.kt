package com.ddd.sikdorok.settings

import androidx.lifecycle.viewModelScope
import com.ddd.sikdorok.core_ui.base.BaseContract
import com.ddd.sikdorok.core_ui.base.BaseViewModel
import com.ddd.sikdorok.domain.settings.GetSettingsUserDeviceInfoUseCase
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
class SettingsViewModel @Inject constructor(
    private val getSettingsUserDeviceInfoUseCase : GetSettingsUserDeviceInfoUseCase
) : BaseViewModel(), BaseContract<SettingsContract.State, SettingsContract.Event, SettingsContract.SideEffect> {

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

    fun getUserDeviceInfo(versionName : String){
        viewModelScope.launch {
            getSettingsUserDeviceInfoUseCase(versionName).data?.let { response ->
                _state.update {
                    it.copy(
                        email =  response.email,
                        nickname = response.nickname ?: "",
                        isNeedUpdate = !response.isLatest,
                        isKakaoUser = response.oauthType == KAKAO_USER_TYPE
                    )
                }
            }
        }
    }

    companion object {
        const val KAKAO_USER_TYPE = "C000200001"
    }

}
