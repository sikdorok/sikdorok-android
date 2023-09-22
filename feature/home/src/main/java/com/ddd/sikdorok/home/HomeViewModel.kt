package com.ddd.sikdorok.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ddd.sikdorok.core_ui.base.BaseViewModel
import com.ddd.sikdorok.core_ui.util.DateUtil
import com.ddd.sikdorok.domain.home.GetHomeDailyFeedsUseCase
import com.ddd.sikdorok.domain.home.GetHomeMonthlyFeedsUseCase
import com.ddd.sikdorok.shared.code.Tag
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
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeDailyFeedsUseCase: GetHomeDailyFeedsUseCase,
    private val getHomeMonthlyFeedsUseCase: GetHomeMonthlyFeedsUseCase
) : BaseViewModel(), HomeContract {

    private val _state = MutableStateFlow(HomeContract.State())
    override val state: StateFlow<HomeContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeContract.Effect>()
    override val effect: SharedFlow<HomeContract.Effect> = _effect.asSharedFlow()

    private val exceptionHandler: CoroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d("HomeViewModel", "Error Occurred! cause ${throwable.message}")
        }
    }

    override fun event(event: HomeContract.Event) {
        fun click(event: HomeContract.Event.Click) {
            viewModelScope.launch {
                when (event) {
                    HomeContract.Event.Click.ChangeDate -> {
                        _effect.emit(HomeContract.Effect.Move.ChangeDate(state.value.nowTime))
                    }
                    HomeContract.Event.Click.ListPage -> {
                        _effect.emit(
                            HomeContract.Effect.Move.ListPage(
                                state.value.nowTime.toString(
                                    "yyyy-MM-dd"
                                )
                            )
                        )
                    }
                    HomeContract.Event.Click.Setting -> {
                        _effect.emit(HomeContract.Effect.Move.Setting)
                    }
                    is HomeContract.Event.Click.Feed -> {
                        _effect.emit(HomeContract.Effect.Move.Feed(event.id))
                    }
                    is HomeContract.Event.Click.Date -> {
                        getWeeklyMealboxInfo(DateUtil.parseDate(event.date))
                    }
                }
            }
        }

        when (event) {
            is HomeContract.Event.Click -> {
                click(event)
            }
            is HomeContract.Event.DeepLink -> {
                viewModelScope.launch {
                    _effect.emit(HomeContract.Effect.Move.DeepLink(event.link))
                }
            }
        }
    }

    fun getWeeklyMealboxInfo(date: DateTime = state.value.nowTime) {
        viewModelScope.launch(exceptionHandler) {
            getHomeMonthlyFeedsUseCase(
                date.toString("yyyy-MM-dd")
            ).data?.let { response ->
                val selectedDate = response.date

                _state.update {
                    it.copy(
                        nowTime = DateTime.parse(
                            selectedDate,
                            DateTimeFormat.forPattern("yyyy-MM-dd")
                        ),
                        weeklyList = response.weeklyCovers.first {
                            it.weeklyFeeds.map { it.time }.contains(selectedDate)
                        }.weeklyFeeds.map {
                            it.copy(
                                isSelected = it.time == date.toString("yyyy-MM-dd")
                            )
                        },
                        weekCount = response.weeklyCovers.first {
                            it.weeklyFeeds.map { it.time }.contains(selectedDate)
                        }.week,
                        nowTag = Tag.MORNING.code
                    )
                }

                // TODO : Paging 적용
                getHomeDailyFeedsUseCase(
                    0,
                    20,
                    selectedDate,
                    state.value.nowTag
                ).data?.let { response ->
                    _state.update {
                        it.copy(
                            nowTag = Tag.MORNING.code,
                            feedList = response.dailyFeeds,
                        )
                    }
                }

            }
        }
    }

    fun changeMealTime(isNext: Boolean) {
        val nowTime = state.value.nowTime.toString("yyyy-MM-dd")
        viewModelScope.launch(exceptionHandler) {
            changeTag(isNext)?.let { nextTag ->
                // TODO : Paging 적용
                getHomeDailyFeedsUseCase(
                    0, 20, nowTime, nextTag
                ).data?.let { response ->
                    _state.update {
                        it.copy(
                            nowTag = nextTag,
                            feedList = response.dailyFeeds,
                        )
                    }
                }
            }
        }
    }

    private fun changeTag(isNext: Boolean): String? {
        val tagList = Tag.values().map { it.code }
        val nextTag: String?
        val nowTag = state.value.nowTag

        nextTag = when (tagList.indexOf(nowTag)) {
            0 -> {
                if (isNext) tagList[tagList.indexOf(nowTag) + 1] else tagList[0]
            }
            in 1..tagList.lastIndex -> {
                if (isNext) {
                    tagList[tagList.indexOf(nowTag) + 1]
                } else {
                    tagList[tagList.indexOf(nowTag) - 1]
                }
            }
            tagList.lastIndex -> {
                if (isNext) tagList[tagList.lastIndex] else tagList[tagList.indexOf(state.value.nowTag) - 1]
            }
            else -> null
        }

        checkTagCanChange()

        return if (nextTag.isNullOrEmpty()) {
            null
        } else nextTag
    }

    private fun checkTagCanChange() {
        val tagList = Tag.values().map { it.code }
        val nowTag = state.value.nowTag

        val result: Pair<Boolean, Boolean> = when (tagList.indexOf(nowTag)) {
            0 -> {
                Pair(false, true)
            }
            in 1..tagList.lastIndex -> {
                Pair(true, true)
            }
            tagList.lastIndex -> {
                Pair(true, false)
            }
            else -> {
                Pair(false, false)
            }
        }

        viewModelScope.launch {
            _state.update {
                it.copy(
                    tagCanGoPrevious = result.first,
                    tagCanGoNext = result.second
                )
            }
        }
    }

    fun onClickCreateFeed() {
        event(HomeContract.Event.Click.Feed())
    }

    fun onClickChangeDate() {
        event(HomeContract.Event.Click.ChangeDate)
    }

    fun onClickListPage() {
        event(HomeContract.Event.Click.ListPage)
    }

    fun onClickSetting() {
        event(HomeContract.Event.Click.Setting)
    }
}