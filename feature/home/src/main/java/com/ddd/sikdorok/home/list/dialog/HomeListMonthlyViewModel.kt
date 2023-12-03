package com.ddd.sikdorok.home.list.dialog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddd.sikdorok.core_ui.base.BaseViewModel
import com.ddd.sikdorok.core_ui.util.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class HomeListMonthlyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel(), HomeListMonthlyContract {

    private val _state = MutableStateFlow(HomeListMonthlyContract.State())
    override val state: StateFlow<HomeListMonthlyContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeListMonthlyContract.Effect>()
    override val effect: SharedFlow<HomeListMonthlyContract.Effect> = _effect.asSharedFlow()

    private val selectedDate: DateTime by lazy {
        val dateString = savedStateHandle.get<String>(KEY_NOW_DATE)
            ?: DateTime.now().toString("yyyy-MM-dd")

        viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedDate = dateString
                )
            }
        }

        DateUtil.parseDate(dateString)
    }

    init {
        selectedDate
        initMonthList()
    }

    override fun event(event: HomeListMonthlyContract.Event) {
        viewModelScope.launch {
            when (event) {
                is HomeListMonthlyContract.Event.ClickMonthDate -> {
                    _state.update { it.copy(selectedDate = event.date) }

                    initMonthList()
                }
                is HomeListMonthlyContract.Event.ClickConfirm -> {
                    _effect.emit(HomeListMonthlyContract.Effect.ChangeMonth(DateUtil.parseDate(state.value.selectedDate)))
                }
                HomeListMonthlyContract.Event.ClickClose -> {
                    _effect.emit(HomeListMonthlyContract.Effect.Close)
                }
            }
        }
    }

    private fun initMonthList() {
        val monthsList = mutableListOf<DateTime>()
        val startDate = DateTime.parse("2023-01-01T00:00")
        val endDate = DateTime.now()
        var currentMonth = startDate

        while (currentMonth.isBefore(endDate) || currentMonth.isEqual(endDate)) {
            monthsList.add(currentMonth)
            currentMonth = currentMonth.plusMonths(1)
        }

        monthsList.reverse()

        viewModelScope.launch {
            _state.update {
                it.copy(
                    monthlyList = monthsList.map {
                        Pair(it, state.value.selectedDate)
                    }
                )
            }
        }
    }

    fun onClickMonth(dateString: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedDate = dateString
                )
            }
        }

        initMonthList()
    }

    fun onClickConfirm() {
        event(HomeListMonthlyContract.Event.ClickConfirm)
    }

    fun onClickClose() {
        event(HomeListMonthlyContract.Event.ClickClose)
    }

    companion object {
        const val KEY_NOW_DATE = "now_date"
    }
}