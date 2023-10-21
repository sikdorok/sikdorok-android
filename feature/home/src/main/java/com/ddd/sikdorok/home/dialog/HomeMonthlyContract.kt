package com.ddd.sikdorok.home.dialog

import com.ddd.sikdorok.core_ui.base.BaseContract
import com.ddd.sikdorok.core_ui.util.DateUtil
import com.ddd.sikdorok.shared.home.WeeklyFeeds
import org.joda.time.DateTime

interface HomeMonthlyContract :
    BaseContract<HomeMonthlyContract.State, HomeMonthlyContract.Event, HomeMonthlyContract.Effect> {

    data class State(
        val viewType: Int = TYPE_WEEKLY,
        val selectedDate: String = DateTime.now().toString("yyyy-MM-dd"),
        val weeklyList: List<WeeklyFeeds> = WeeklyFeeds.emptyList,
        val monthlyList: List<Pair<DateTime, String>> = emptyList()
    ) {
        val selectedMonthText: String =
            DateUtil.parseDate(selectedDate).toString("yyyy년 MM월")
    }

    sealed interface Event {
        data class ClickWeeklyDate(val date: String) : Event
        object ClickPreviousMonth : Event
        object ClickNextMonth : Event
        object ClickClose : Event

        sealed interface Month : Event{
            object Cancel : Month
            object Confirm : Month
        }
    }

    sealed interface Effect {
        data class GoMainFeed(val date: DateTime) : Effect
        object Close : Effect
    }

    companion object {
        const val TYPE_WEEKLY = 0
        const val TYPE_MONTHLY = 1
    }
}