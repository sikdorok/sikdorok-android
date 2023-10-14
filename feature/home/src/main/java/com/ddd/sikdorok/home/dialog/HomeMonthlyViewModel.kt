package com.ddd.sikdorok.home.dialog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddd.sikdorok.core_ui.base.BaseViewModel
import com.ddd.sikdorok.core_ui.util.DateUtil
import com.ddd.sikdorok.domain.home.GetHomeMonthlyFeedsUseCase
import com.ddd.sikdorok.shared.home.WeeklyFeeds
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
class HomeMonthlyViewModel @Inject constructor(
    private val getHomeMonthlyFeedsUseCase: GetHomeMonthlyFeedsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel(), HomeMonthlyContract {

    private val _state = MutableStateFlow(HomeMonthlyContract.State())
    override val state: StateFlow<HomeMonthlyContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeMonthlyContract.Effect>()
    override val effect: SharedFlow<HomeMonthlyContract.Effect> = _effect.asSharedFlow()

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

    private var selectedMonth = state.value.selectedDate

    init {
        initMonthList()
        getWeekFeeds(selectedDate)
    }

    override fun event(event: HomeMonthlyContract.Event) {
        fun month(event: HomeMonthlyContract.Event.Month) {
            viewModelScope.launch {
                when (event) {
                    HomeMonthlyContract.Event.Month.Cancel -> {
                        _effect.emit(HomeMonthlyContract.Effect.Close)
                    }
                    HomeMonthlyContract.Event.Month.Confirm -> {
                        changeMonth()
                    }
                }
            }

        }


        viewModelScope.launch {
            when (event) {
                HomeMonthlyContract.Event.ClickPreviousMonth -> {
                    getWeekFeeds(DateUtil.parseDate(state.value.selectedDate).minusMonths(1))
                }
                HomeMonthlyContract.Event.ClickNextMonth -> {
                    getWeekFeeds(DateUtil.parseDate(state.value.selectedDate).plusMonths(1))
                }
                is HomeMonthlyContract.Event.ClickWeeklyDate -> {
                    _state.update {
                        it.copy(
                            selectedDate = event.date
                        )
                    }

                    _effect.emit(
                        HomeMonthlyContract.Effect.GoMainFeed(DateUtil.parseDate(event.date))
                    )
                }
                HomeMonthlyContract.Event.ClickClose -> {
                    _effect.emit(HomeMonthlyContract.Effect.Close)
                }
                is HomeMonthlyContract.Event.Month -> {
                    month(event)
                }
            }
        }
    }

    private fun getWeekFeeds(date: DateTime = selectedDate) {
        viewModelScope.launch {
            getHomeMonthlyFeedsUseCase(date.toString("yyyy-MM-dd")).data?.let { response ->

                val weekList = mutableListOf<WeeklyFeeds>()
                response.weeklyCovers.forEach {
                    val week = if (it.weeklyFeeds.map { it.time }
                            .contains(selectedDate.toString("yyyy-MM-dd"))) {
                        it.weeklyFeeds.map {
                            it.copy(
                                isSelected = it.time == date.toString("yyyy-MM-dd")
                            )
                        }
                    } else it.weeklyFeeds

                    weekList.addAll(week)
                }

                _state.update {
                    it.copy(
                        selectedDate = date.toString("yyyy-MM-dd"),
                        weeklyList = weekList
                    )
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

        viewModelScope.launch {
            _state.update {
                it.copy(
                    monthlyList = monthsList.map {
                        Pair(it, selectedMonth)
                    }
                )
            }
        }
    }

    fun onClickPreviousMonth() {
        event(HomeMonthlyContract.Event.ClickPreviousMonth)
    }

    fun onClickNextMonth() {
        event(HomeMonthlyContract.Event.ClickNextMonth)
    }

    fun onClickChangeViewType() {
        val changedViewType = when (state.value.viewType) {
            HomeMonthlyContract.TYPE_MONTHLY -> {
                HomeMonthlyContract.TYPE_WEEKLY
            }
            HomeMonthlyContract.TYPE_WEEKLY -> {
                HomeMonthlyContract.TYPE_MONTHLY
            }
            else -> null
        }

        changedViewType?.let { viewType ->
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        viewType = viewType
                    )
                }
            }
        }
    }

    fun onClickMonth(dateString: String) {
        selectedMonth = dateString

        initMonthList()
    }

    fun onClickClose() {
        event(HomeMonthlyContract.Event.ClickClose)
    }

    fun onClickMonthCancel() {
        event(HomeMonthlyContract.Event.Month.Cancel)
    }

    fun onClickMonthConfirm() {
        event(HomeMonthlyContract.Event.Month.Confirm)
    }

    fun changeMonth() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedDate = selectedMonth
                )
            }
            initMonthList()
            getWeekFeeds(DateUtil.parseDate(selectedMonth))
        }
    }

    companion object {
        const val KEY_NOW_DATE = "now_date"
    }
}