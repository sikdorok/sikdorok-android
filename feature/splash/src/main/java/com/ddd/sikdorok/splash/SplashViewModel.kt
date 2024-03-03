package com.ddd.sikdorok.splash

import androidx.lifecycle.viewModelScope
import com.ddd.sikdorok.core_ui.base.BaseViewModel
import com.ddd.sikdorok.domain.login.GetSavedTokenUseCase
import com.ddd.sikdorok.domain.settings.GetAppInfoUseCase
import com.ddd.sikdorok.shared.base.onFailure
import com.ddd.sikdorok.shared.base.onSuccess
import com.ddd.sikdorok.shared.key.Keys.ACCESS_TOKEN
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
class SplashViewModel @Inject constructor(
    private val getAppInfoUseCase: GetAppInfoUseCase,
    private val getSavedTokenUseCase: GetSavedTokenUseCase
) : BaseViewModel(), SplashContract {

    private val _state = MutableStateFlow(SplashContract.State())
    override val state: StateFlow<SplashContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<SplashContract.Effect>()
    override val effect: SharedFlow<SplashContract.Effect> = _effect.asSharedFlow()

    private var deeplinkReceived: Boolean = false
    var isNeedForceUpdate: Boolean = false

    override fun event(event: SplashContract.Event) {
        viewModelScope.launch {
            when (event) {
                SplashContract.Event.LoginCheck -> {
                    if (deeplinkReceived.not()) { // 딥링크 리스너 동작 시 동시 동작 X
                        if (getSavedTokenUseCase.invoke(ACCESS_TOKEN).isEmpty()) {
                            _effect.emit(SplashContract.Effect.NaviToSignIn)
                        } else {
                            _effect.emit(SplashContract.Effect.GoToMain())
                        }
                    }
                }
                is SplashContract.Event.DeepLink -> {
                    deeplinkReceived = true
                    _effect.emit(SplashContract.Effect.GoToMain(event.deeplink))
                }
            }
        }
    }

    fun getAppVersionInfo(version: String) {
        viewModelScope.launch {
            getAppInfoUseCase()
                .onSuccess {

                    val major = it?.data?.appVersion?.major ?: 0
                    val minor = it?.data?.appVersion?.minor ?: 0
                    val patch = it?.data?.appVersion?.patch ?: 0

                    val requireVersion = SemanticVersion(major, minor, patch)

                    val myMajor: Int = version.split(".").getOrNull(0)?.toInt() ?: 0
                    val myMinor: Int = version.split(".").getOrNull(1)?.toInt() ?: 0
                    val myPatch: Int = version.split(".").getOrNull(2)?.toInt() ?: 0

                    val myVersion = SemanticVersion(myMajor, myMinor, myPatch)

                    if (myVersion.isNeedToUpdate(requireVersion) &&
                        it?.data?.appVersion?.forceUpdateStatus == true
                    ) {
                        isNeedForceUpdate = true
                        _effect.emit(SplashContract.Effect.NeedUpdate)
                    } else {
                        event(SplashContract.Event.LoginCheck)
                    }
                }.onFailure {
                    event(SplashContract.Event.LoginCheck)
                }
        }
    }
}
