package com.ddd.sikdorok.core_ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

open class BaseViewModel : ViewModel() {

    fun <T> Flow<T>.toState(initialValue: T): StateFlow<T> {
        return stateIn(
            initialValue = initialValue,
            started = SharingStarted.WhileSubscribed(5000),
            scope = viewModelScope
        )
    }
}
