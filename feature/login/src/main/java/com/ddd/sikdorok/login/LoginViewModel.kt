package com.ddd.sikdorok.login

import androidx.lifecycle.viewModelScope
import com.ddd.sikdorok.core_ui.base.BaseContract
import com.ddd.sikdorok.core_ui.base.BaseViewModel
import com.ddd.sikdorok.domain.login.PostOnCheckUserUseCase
import com.ddd.sikdorok.domain.login.PostSaveTokenUseCase
import com.ddd.sikdorok.shared.login.TokenType
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
class LoginViewModel @Inject constructor(
    private val postOnCheckUserUseCase: PostOnCheckUserUseCase,
    private val postSaveTokenUseCase: PostSaveTokenUseCase
) : BaseViewModel(),
    BaseContract<LoginContract.State, LoginContract.Event, LoginContract.SideEffect> {

    private val _state = MutableStateFlow(LoginContract.State())
    override val state: StateFlow<LoginContract.State>
        get() = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LoginContract.SideEffect>()
    override val effect: SharedFlow<LoginContract.SideEffect>
        get() = _effect.asSharedFlow()

    override fun event(event: LoginContract.Event) {
        viewModelScope.launch {
            when (event) {
                is LoginContract.Event.RequestKakaoLogin -> {
                    _effect.emit(LoginContract.SideEffect.NaviToKakaoLogin)
                }
                is LoginContract.Event.RequestSikdorokLogin -> {
                    _effect.emit(LoginContract.SideEffect.NaviToSikdorokLogin)
                }
                is LoginContract.Event.CheckKakaoUser -> {
                    val result = postOnCheckUserUseCase(event.code)

                    if (result.data?.isRegistered == true) {
                        val accessToken = result.data?.login?.accessToken
                        val refreshToken = result.data?.login?.refreshToken

                        if (accessToken.isNullOrEmpty().not()) {
                            postSaveTokenUseCase.invoke(
                                TokenType.REFRESH_TOKEN,
                                refreshToken.orEmpty()
                            )
                                .mapCatching {
                                    postSaveTokenUseCase.invoke(
                                        TokenType.ACCESS_TOKEN,
                                        accessToken.orEmpty()
                                    )
                                }.fold(
                                    onSuccess = {
                                        _effect.emit(LoginContract.SideEffect.NaviToHome)
                                    },
                                    onFailure = {
                                        it.printStackTrace()
                                    }
                                )
                        } else {
                            _effect.emit(LoginContract.SideEffect.NaviToSignUp(result.data?.login?.email))
                        }
                    } else {
                        _effect.emit(LoginContract.SideEffect.NaviToSignUp(result.data?.login?.email))
                    }
                }
            }
        }
    }
}
