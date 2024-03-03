package com.ddd.sikdorok.find_password

import androidx.lifecycle.viewModelScope
import com.ddd.sikdorok.core_ui.base.BaseContract
import com.ddd.sikdorok.core_ui.base.BaseViewModel
import com.ddd.sikdorok.domain.password.PostFindPasswordUseCase
import com.ddd.sikdorok.shared.base.onFailure
import com.ddd.sikdorok.shared.base.onSuccess
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
class FindPasswordViewModel @Inject constructor(
    private val postFindPasswordUseCase: PostFindPasswordUseCase
) : BaseViewModel(),
    BaseContract<FindPasswordContract.State, FindPasswordContract.Event, FindPasswordContract.SideEffect> {

    private val _effect = MutableSharedFlow<FindPasswordContract.SideEffect>()
    override val effect: SharedFlow<FindPasswordContract.SideEffect>
        get() = _effect.asSharedFlow()

    private val _state = MutableStateFlow(FindPasswordContract.State())
    override val state: StateFlow<FindPasswordContract.State>
        get() = _state.asStateFlow()

    private val emailRegex = Regex("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b")

    override fun event(event: FindPasswordContract.Event) {
        viewModelScope.launch {
            when (event) {
                is FindPasswordContract.Event.InputEmail -> {
                    if (event.value.length in 2..25 && event.value.matches(emailRegex)) {
                        _effect.emit(FindPasswordContract.SideEffect.ValidateEmail)
                        _state.update { it.copy(email = event.value) }
                    } else {
                        _effect.emit(FindPasswordContract.SideEffect.InValidateEmail)
                        _state.update { it.copy(email = "") }
                    }
                }
                FindPasswordContract.Event.OnClickLeftIcon -> {
                    _effect.emit(FindPasswordContract.SideEffect.NaviToBack)
                }
                is FindPasswordContract.Event.Submit -> {
                    postFindPasswordUseCase(event.email)
                        .onSuccess { result ->
                            _effect.emit(FindPasswordContract.SideEffect.NaviToSuccess(event.email))
                        }.onFailure { error ->
                            _effect.emit(FindPasswordContract.SideEffect.ShowSnackBar(error.message))
                        }
                }
            }
        }
    }
}
