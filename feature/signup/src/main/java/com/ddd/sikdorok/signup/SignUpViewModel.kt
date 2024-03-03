package com.ddd.sikdorok.signup

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddd.sikdorok.core_ui.base.BaseContract
import com.ddd.sikdorok.core_ui.base.BaseViewModel
import com.ddd.sikdorok.domain.email.PostOnCheckEmailUseCase
import com.ddd.sikdorok.domain.login.PostSaveTokenUseCase
import com.ddd.sikdorok.domain.signup.PostSignUpUseCase
import com.ddd.sikdorok.shared.base.onFailure
import com.ddd.sikdorok.shared.base.onSuccess
import com.ddd.sikdorok.shared.login.TokenType
import com.ddd.sikdorok.shared.sign.SignUp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
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
class SignUpViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val onPostSignUpUseCase: PostSignUpUseCase,
    private val onPostEmailCheckUseCase: PostOnCheckEmailUseCase,
    private val onPostSaveTokenUseCase: PostSaveTokenUseCase
) : BaseViewModel(),
    BaseContract<SignUpContract.State, SignUpContract.Event, SignUpContract.SideEffect> {

    private val email: String
        get() = savedStateHandle.get<String>(PAYLOAD).orEmpty()

    private val _effect = MutableSharedFlow<SignUpContract.SideEffect>()
    override val effect: SharedFlow<SignUpContract.SideEffect>
        get() = _effect.asSharedFlow()

    private val _state = MutableStateFlow(
        SignUpContract.State(
            email = email
        )
    )
    override val state: StateFlow<SignUpContract.State>
        get() = _state.asStateFlow()

    private val emailRegex = Regex("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b")
    private val passwordRegex =
        """^(?=.*[a-zA-Z0-9!@#\$%^&*()\\-_=+|{}\\[\\]:;<>,./?]).{8,}$""".toRegex()

    override fun event(event: SignUpContract.Event) {
        viewModelScope.launch {
            when (event) {
                is SignUpContract.Event.EmailCheck -> {
                    if (event.email.matches(emailRegex)) {
                        val isAlreadyUser = onPostEmailCheckUseCase(event.email).getOrNull()

                        if (isAlreadyUser?.data == false || isAlreadyUser == null) {
                            _effect.emit(SignUpContract.SideEffect.ValidateEmail)
                        } else {
                            _effect.emit(SignUpContract.SideEffect.AlreadyRegisteredEmail)
                        }
                        _state.update { _state.value.copy(email = event.email) }
                    } else {
                        _effect.emit(SignUpContract.SideEffect.InValidateEmailFormat)
                        _state.update { _state.value.copy(email = "") }
                    }
                }
                is SignUpContract.Event.InputName -> {
                    if (event.name.length in 2..10) {
                        _effect.emit(SignUpContract.SideEffect.ValidateName)
                        _state.update { _state.value.copy(name = event.name) }
                    } else {
                        _effect.emit(SignUpContract.SideEffect.InValidateName)
                        _state.update { _state.value.copy(name = "") }
                    }
                }
                is SignUpContract.Event.PasswordCheck -> {
                    if (event.isSame) {
                        _effect.emit(SignUpContract.SideEffect.ValidatePasswordCheck)
                    } else {
                        _effect.emit(SignUpContract.SideEffect.InValidatePasswordCheck)
                    }
                }
                is SignUpContract.Event.InputPassword -> {
                    if (event.password.length in 8..20 && event.password.matches(passwordRegex)) {
                        _effect.emit(SignUpContract.SideEffect.ValidatePassword)
                        _state.update { _state.value.copy(password = event.password) }
                    } else {
                        _effect.emit(SignUpContract.SideEffect.InValidatePassword)
                        _state.update { _state.value.copy(password = "") }
                    }
                }
                is SignUpContract.Event.InputPasswordCheck -> {
                    if (event.password.length in 8..20) {
                        _state.update { _state.value.copy(passwordCheck = event.password) }
                    } else {
                        _state.update { _state.value.copy(passwordCheck = "") }
                    }
                }
                is SignUpContract.Event.SignUp -> {
                    viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
                        Log.e("error", throwable.stackTraceToString())
                    }) {
                        onPostSignUpUseCase(
                            SignUp.Request(
                                oauthType = event.oauthType,
                                oauthId = event.oauthId,
                                nickname = event.nickname,
                                email = event.email,
                                password = event.password,
                                passwordCheck = event.passwordCheck
                            )
                        ).onSuccess { result ->
                            val accessToken = result?.data?.login?.accessToken
                            val refreshToken = result?.data?.login?.refreshToken

                            if (accessToken.isNullOrEmpty().not()) {
                                onPostSaveTokenUseCase.invoke(
                                    TokenType.REFRESH_TOKEN,
                                    refreshToken.orEmpty()
                                )
                                onPostSaveTokenUseCase.invoke(
                                    TokenType.ACCESS_TOKEN,
                                    accessToken.orEmpty()
                                )
                                _effect.emit(SignUpContract.SideEffect.NaviToHome)
                            } else {
                                _effect.emit(SignUpContract.SideEffect.SnowSnackBar(result?.message.orEmpty()))
                            }
                        }.onFailure {
                            _effect.emit(SignUpContract.SideEffect.SnowSnackBar("오류가 발생했습니다. 다시 시도해 주세요"))
                        }
                    }
                }
                is SignUpContract.Event.OnBackPressed -> {
                    _effect.emit(SignUpContract.SideEffect.NaviToBack)
                }
            }
        }
    }

    companion object {
        private const val PAYLOAD = "payload"
    }
}
