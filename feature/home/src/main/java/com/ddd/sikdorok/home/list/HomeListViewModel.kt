package com.ddd.sikdorok.home.list

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddd.sikdorok.core_ui.base.BaseViewModel
import com.ddd.sikdorok.core_ui.util.DateUtil
import com.ddd.sikdorok.domain.home.GetHomeListFeedsUseCase
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
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class HomeListViewModel @Inject constructor(
    private val getHomeListFeedsUseCase: GetHomeListFeedsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel(), HomeListContract {

    private val _state = MutableStateFlow(HomeListContract.State())
    override val state: StateFlow<HomeListContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeListContract.Effect>()
    override val effect: SharedFlow<HomeListContract.Effect> = _effect.asSharedFlow()

    private val selectedDate: DateTime by lazy {
        val dateString = savedStateHandle.get<String>(KEY_SELECTED_DATE)
            ?: DateTime.now().toString("yyyy-MM-dd")

        viewModelScope.launch {
            _state.update {
                it.copy(
                    nowTime = DateUtil.parseDate(dateString)
                )
            }
        }

        DateUtil.parseDate(dateString)
    }


    private val exceptionHandler: CoroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d("HomeListViewModel", "Error Occurred! cause ${throwable.message}")
        }
    }

    override fun event(event: HomeListContract.Event) {
        fun click(event: HomeListContract.Event.Click) {
            viewModelScope.launch {
                when (event) {
                    HomeListContract.Event.Click.ChangeDate -> {
                        _effect.emit(HomeListContract.Effect.Move.ChangeDate(state.value.nowTime))
                    }
                    is HomeListContract.Event.Click.Feed -> {
                        _effect.emit(HomeListContract.Effect.Move.Feed(event.id))
                    }
                }
            }
        }

        when (event) {
            is HomeListContract.Event.Click -> {
                click(event)
            }
        }
    }

    fun getWeeklyMealList(date: DateTime = selectedDate) {
        showLoading()

        viewModelScope.launch(exceptionHandler) {
            getHomeListFeedsUseCase(
                size = 20,
                date = date.toString("yyyy-MM-dd"),
                cursorDate = null
            ).onSuccess {
                it?.data?.let { response ->

                    _state.update {
                        it.copy(
                            nowTime = date,
                            feedList = response.dailyFeeds
                        )
                    }

                    checkArrowEnabled(date)
                    hideLoading()
                }
            }
        }
    }

    private fun checkArrowEnabled(date: DateTime) {
        viewModelScope.launch {
            val startDate = DateTime.parse("2023-01-01T00:00")
            val endDate = DateTime.now()

            val canGoPrevious = startDate.toString("yyyy-MM") != date.toString("yyyy-MM")
            val canGoNext = endDate.toString("yyyy-MM") != date.toString("yyyy-MM")

            _state.update {
                it.copy(
                    canGoPrevious = canGoPrevious,
                    canGoNext = canGoNext
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

    fun onClickSelectDate() {
        event(HomeListContract.Event.Click.ChangeDate)
    }

    fun onClickChangePreviousMonth() {
        if (state.value.canGoPrevious) {
            getWeeklyMealList(state.value.nowTime.minusMonths(1))
        }
    }

    fun onClickChangeNextMonth() {
        if (state.value.canGoNext) {
            getWeeklyMealList(state.value.nowTime.plusMonths(1))
        }
    }

    companion object {
        const val KEY_SELECTED_DATE = "selected_date"
    }
}