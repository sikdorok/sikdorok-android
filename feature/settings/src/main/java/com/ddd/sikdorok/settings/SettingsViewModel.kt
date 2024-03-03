package com.ddd.sikdorok.settings

import androidx.lifecycle.viewModelScope
import com.ddd.sikdorok.core_ui.base.BaseContract
import com.ddd.sikdorok.core_ui.base.BaseViewModel
import com.ddd.sikdorok.domain.login.PostSaveTokenUseCase
import com.ddd.sikdorok.domain.settings.GetSettingsUserDeviceInfoUseCase
import com.ddd.sikdorok.domain.settings.SetUserLogoutUseCase
import com.ddd.sikdorok.shared.base.onFailure
import com.ddd.sikdorok.shared.base.onSuccess
import com.ddd.sikdorok.shared.login.TokenType
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
    private val getSettingsUserDeviceInfoUseCase: GetSettingsUserDeviceInfoUseCase,
    private val setUserLogoutUseCase: SetUserLogoutUseCase,
    private val postSaveTokenUseCase: PostSaveTokenUseCase
) : BaseViewModel(),
    BaseContract<SettingsContract.State, SettingsContract.Event, SettingsContract.SideEffect> {

    private val _state = MutableStateFlow(SettingsContract.State())
    override val state: StateFlow<SettingsContract.State>
        get() = _state.asStateFlow()

    private val _effect = MutableSharedFlow<SettingsContract.SideEffect>()
    override val effect: SharedFlow<SettingsContract.SideEffect>
        get() = _effect.asSharedFlow()

    override fun event(event: SettingsContract.Event) {
        viewModelScope.launch {
            when (event) {
                SettingsContract.Event.OnClickPolicy -> {
                    _effect.emit(SettingsContract.SideEffect.NaviToPolicy)
                }
                SettingsContract.Event.OnClickAccountDelete -> {
                    _effect.emit(SettingsContract.SideEffect.NaviToDeleteAccount)
                }
                SettingsContract.Event.OnClickProfileManage -> {
                    _effect.emit(SettingsContract.SideEffect.NaviToProfileManage)
                }
                SettingsContract.Event.OnClickLogout -> {
                    _effect.emit(SettingsContract.SideEffect.Logout)
                }
                SettingsContract.Event.OnClickPlayStore -> {
                    _effect.emit(SettingsContract.SideEffect.PlayStore)
                }
            }
        }
    }

    fun getUserDeviceInfo(versionName: String) {
        viewModelScope.launch {
            getSettingsUserDeviceInfoUseCase(versionName).onSuccess {
                it?.data?.let { response ->
                    _state.update {
                        it.copy(
                            email = response.email,
                            nickname = response.nickname ?: "",
                            isNeedUpdate = !response.isLatest,
                            isKakaoUser = response.oauthType == KAKAO_USER_TYPE
                        )
                    }
                }
            }.onFailure { }
        }
    }

    fun setUserLogout() {
        viewModelScope.launch {
            setUserLogoutUseCase().apply {
                postSaveTokenUseCase(TokenType.ACCESS_TOKEN, "")
                postSaveTokenUseCase(TokenType.REFRESH_TOKEN, "")

                _effect.emit(SettingsContract.SideEffect.NaviToSplash)

            }
        }
    }

    fun onClickToPlayStore(){
        if(state.value.isNeedUpdate) {
            event(SettingsContract.Event.OnClickPlayStore)
        }
    }

    fun onClickLogout() {
        event(SettingsContract.Event.OnClickLogout)
    }

    companion object {
        const val KAKAO_USER_TYPE = "C000200001"
    }

}
