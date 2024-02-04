package com.ddd.sikdorok.delete_account

import androidx.lifecycle.viewModelScope
import com.ddd.sikdorok.core_ui.base.BaseViewModel
import com.ddd.sikdorok.domain.withdraw.DeleteUserAllSettingsUseCase
import com.ddd.sikdorok.domain.withdraw.DeleteUserWithDrawUseCase
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
class DeleteAccountViewModel @Inject constructor(
    private val deleteUserWithDrawUseCase: DeleteUserWithDrawUseCase,
    private val deleteUserAllSettingsUseCase: DeleteUserAllSettingsUseCase
) : BaseViewModel(), DeleteAccountContract {

    private val _state = MutableStateFlow(DeleteAccountContract.State())
    override val state: StateFlow<DeleteAccountContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<DeleteAccountContract.Effect>()
    override val effect: SharedFlow<DeleteAccountContract.Effect> = _effect.asSharedFlow()

    override fun event(event: DeleteAccountContract.Event) {
        viewModelScope.launch {
            when (event) {
                DeleteAccountContract.Event.OnClickDeleteAccount -> {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    showLoading()
                    requestDeleteAccount()
                }
            }
        }
    }

    private suspend fun requestDeleteAccount() {
        deleteUserWithDrawUseCase()
            .onSuccess {
                hideLoading()
                deleteUserAllSettingsUseCase()
                _effect.emit(DeleteAccountContract.Effect.GoToMain)
            }
            .onFailure {
                hideLoading()
                _effect.emit(DeleteAccountContract.Effect.ShowSnackBar("오류가 발생했습니다. 다시 시도해 주세요"))
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