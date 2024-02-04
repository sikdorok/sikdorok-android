package com.ddd.sikdorok.delete_account

import com.ddd.sikdorok.core_ui.base.BaseContract

interface DeleteAccountContract :
    BaseContract<DeleteAccountContract.State, DeleteAccountContract.Event, DeleteAccountContract.Effect> {

    data class State(
        val isLoading: Boolean = false
    )

    sealed interface Event {
        object OnClickDeleteAccount : Event
    }

    sealed interface Effect {
        object GoToMain : Effect
        data class ShowSnackBar(val text: String) : Effect
    }
}