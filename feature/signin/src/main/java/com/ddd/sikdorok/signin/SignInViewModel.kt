package com.ddd.sikdorok.signin

import androidx.lifecycle.viewModelScope
import com.ddd.sikdorok.core_ui.base.BaseContract
import com.ddd.sikdorok.core_ui.base.BaseViewModel
import com.ddd.sikdorok.domain.login.PostSaveTokenUseCase
import com.ddd.sikdorok.domain.login.PostSikdorokLocalLoginUseCase
import com.ddd.sikdorok.shared.login.Request
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

@Suppress("SpellCheckingInspection")
@HiltViewModel
class SignInViewModel @Inject constructor(
    private val postSikdorokLocalLoginUseCase: PostSikdorokLocalLoginUseCase,
    private val postSaveTokenUseCase: PostSaveTokenUseCase
): BaseViewModel(),
    BaseContract<SignInContract.State, SignInContract.Event, SignInContract.SideEffect> {
    private val _effect = MutableSharedFlow<SignInContract.SideEffect>()
    override val effect: SharedFlow<SignInContract.SideEffect>
        get() = _effect.asSharedFlow()

    private val _state = MutableStateFlow(SignInContract.State())
    override val state: StateFlow<SignInContract.State>
        get() = _state.asStateFlow()

    override fun event(event: SignInContract.Event) {
        viewModelScope.launch {
            when(event) {
                is SignInContract.Event.OnBackPressed -> {
                    _effect.emit(SignInContract.SideEffect.NaviToBack)
                }
                is SignInContract.Event.NaviToSignUp -> {
                    _effect.emit(SignInContract.SideEffect.NaviToSignUp)
                }
                is SignInContract.Event.NaviToFindPassword -> {
                    _effect.emit(SignInContract.SideEffect.NaviToFindPassword)
                }
                is SignInContract.Event.OnClickSubmit -> {
                    postSikdorokLocalLoginUseCase.invoke(
                        Request.Sikdorok(event.email, event.password)
                    ).fold(
                        onSuccess = { result ->
                            if(result.data != null) {
                                postSaveTokenUseCase.invoke(
                                    TokenType.ACCESS_TOKEN,
                                    result.data?.login?.accessToken.orEmpty()
                                )
                                postSaveTokenUseCase.invoke(
                                    TokenType.REFRESH_TOKEN,
                                    result.data?.login?.refreshToken.orEmpty()
                                )

                                _effect.emit(SignInContract.SideEffect.NaviToHome)

                            } else {
                                _effect.emit(SignInContract.SideEffect.ShowSnackBar(result.message))
                            }
                        },
                        onFailure = {
                            _effect.emit(SignInContract.SideEffect.ShowSnackBar(it.message.orEmpty()))
                        }
                    )
                }
            }
        }
    }
}
