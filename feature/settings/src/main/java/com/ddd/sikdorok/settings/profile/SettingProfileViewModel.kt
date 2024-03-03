package com.ddd.sikdorok.settings.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ddd.sikdorok.core_ui.base.BaseContract
import com.ddd.sikdorok.core_ui.base.BaseViewModel
import com.ddd.sikdorok.domain.settings.GetUserProfileUseCase
import com.ddd.sikdorok.domain.settings.PetUserProfileUseCase
import com.ddd.sikdorok.shared.base.onFailure
import com.ddd.sikdorok.shared.base.onSuccess
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
class SettingProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val putUserProfileUseCase: PetUserProfileUseCase
) : BaseViewModel(),
    BaseContract<SettingProfileContract.State, SettingProfileContract.Event, SettingProfileContract.SideEffect> {

    private val _effect = MutableSharedFlow<SettingProfileContract.SideEffect>()
    override val effect: SharedFlow<SettingProfileContract.SideEffect>
        get() = _effect.asSharedFlow()

    private val _state = MutableStateFlow(SettingProfileContract.State())
    override val state: StateFlow<SettingProfileContract.State>
        get() = _state.asStateFlow()

    override fun event(event: SettingProfileContract.Event) {
        viewModelScope.launch {
            when (event) {
                is SettingProfileContract.Event.NameCheck -> {
                    if (event.name.length in 2..10) {
                        _effect.emit(SettingProfileContract.SideEffect.ValidateName)
                        _state.update { _state.value.copy(name = event.name) }
                    } else {
                        _effect.emit(SettingProfileContract.SideEffect.InValidateName)
                        _state.update { _state.value.copy(name = event.name) }
                    }
                }
                is SettingProfileContract.Event.EditProfile -> {
                    viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
                        Log.e("error", throwable.stackTraceToString())
                    }) {
                        putUserProfileUseCase(
                            event.name
                        ).onSuccess { result ->
                            hideLoading()
                            _effect.emit(SettingProfileContract.SideEffect.NaviToSuccess)
                        }.onFailure {
                            _effect.emit(SettingProfileContract.SideEffect.SnowSnackBar("오류가 발생했습니다. 다시 시도해 주세요"))
                        }
                    }
                }
                is SettingProfileContract.Event.OnBackPressed -> {
                    _effect.emit(SettingProfileContract.SideEffect.NaviToBack)
                }
            }
        }
    }

    fun getUserProfile() {
        viewModelScope.launch {
            getUserProfileUseCase()
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            name = response?.data?.nickname.orEmpty(),
                            email = response?.data?.email.orEmpty()
                        )
                    }
                }
                .onFailure {
                    _effect.emit(SettingProfileContract.SideEffect.SnowSnackBar("오류가 발생했습니다. 다시 시도해 주세요"))
                }
        }
    }

    private fun showLoading() {
        _state.update {
            it.copy(isLoading = true)
        }
    }

    private fun hideLoading() {
        _state.update {
            it.copy(isLoading = false)
        }
    }

    companion object {
        private const val PAYLOAD = "payload"
    }
}
