package com.ddd.sikdorok.reset_password

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddd.sikdorok.core_ui.base.BaseContract
import com.ddd.sikdorok.core_ui.base.BaseViewModel
import com.ddd.sikdorok.domain.password.ResetPasswordUseCase
import com.ddd.sikdorok.domain.password.VerifyPasswordLinkUseCase
import com.ddd.sikdorok.shared.base.onFailure
import com.ddd.sikdorok.shared.base.onSuccess
import com.ddd.sikdorok.shared.password.Reset
import com.ddd.sikdorok.shared.password.Verify
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
class ResetPasswordViewModel @Inject constructor(
    private val verifyPasswordLinkUseCase: VerifyPasswordLinkUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel(),
    BaseContract<ResetPasswordContract.State, ResetPasswordContract.Event, ResetPasswordContract.SideEffect> {

    private val userId: String by lazy {
        savedStateHandle.get<String>("userId").orEmpty()
    }

    private val code: String by lazy {
        savedStateHandle.get<String>("code").orEmpty()
    }

    private val _effect = MutableSharedFlow<ResetPasswordContract.SideEffect>()
    override val effect: SharedFlow<ResetPasswordContract.SideEffect>
        get() = _effect.asSharedFlow()

    private val _state = MutableStateFlow(ResetPasswordContract.State())
    override val state: StateFlow<ResetPasswordContract.State>
        get() = _state.asStateFlow()

    private val passwordRegex =
        """^(?=.*[a-zA-Z0-9!@#\$%^&*()\\-_=+|{}\\[\\]:;<>,./?]).{8,}$""".toRegex()

    override fun event(event: ResetPasswordContract.Event) {
        viewModelScope.launch {
            when (event) {
                is ResetPasswordContract.Event.InputPassword -> {
                    if (event.password.length in 8..20 && event.password.matches(passwordRegex)) {
                        _effect.emit(ResetPasswordContract.SideEffect.ValidatePassword)
                        _state.update { _state.value.copy(password = event.password) }
                    } else {
                        _effect.emit(ResetPasswordContract.SideEffect.InValidatePassword)
                        _state.update { _state.value.copy(password = "") }
                    }
                }
                is ResetPasswordContract.Event.InputPasswordConfirm -> {
                    if (event.password.length in 8..20) {
                        _state.update { _state.value.copy(confirmPassword = event.password) }
                    } else {
                        _state.update { _state.value.copy(confirmPassword = "") }
                    }
                }
                ResetPasswordContract.Event.OnClickLeftIcon -> {
                    _effect.emit(ResetPasswordContract.SideEffect.NaviToBack)
                }
                ResetPasswordContract.Event.Submit -> {
                    resetPassword()
                }
            }
        }
    }

    fun checkIsValidInfo() {
        showLoading()
        viewModelScope.launch {
            verifyPasswordLinkUseCase(
                Verify.Request(
                    userId = userId,
                    code = code
                )
            )
                .onSuccess {
                    hideLoading()
                }
                .onFailure {
                    hideLoading()
                    _effect.emit(ResetPasswordContract.SideEffect.ShowSnackBar("에러가 발생했습니다. 다시 시도해 주세요"))
                }
        }
    }

    private fun resetPassword() {
        showLoading()
        viewModelScope.launch {
            resetPasswordUseCase(
                Reset.Request(
                    userId = userId,
                    password = state.value.password,
                    passwordCheck = state.value.password
                )
            )
                .onSuccess { result ->
                    hideLoading()
                    _effect.emit(ResetPasswordContract.SideEffect.NaviToSuccess)
                }.onFailure { error ->
                    hideLoading()
                    _effect.emit(
                        ResetPasswordContract.SideEffect.ShowSnackBar(
                            "에러가 발생했습니다. 다시 시도해 주세요"
                        )
                    )
                }
        }

    }

    private fun showLoading() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
    }

    private fun hideLoading() {
        _state.update {
            it.copy(
                isLoading = false
            )
        }
    }

}
