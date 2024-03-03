package com.ddd.sikdorok.home

import com.ddd.sikdorok.core_ui.base.BaseContract
import com.ddd.sikdorok.core_ui.util.DateUtil
import com.ddd.sikdorok.shared.code.Tag
import com.ddd.sikdorok.shared.home.HomeDailyFeed
import com.ddd.sikdorok.shared.home.HomeListPaging
import com.ddd.sikdorok.shared.home.WeeklyFeeds
import org.joda.time.DateTime

interface HomeContract :
    BaseContract<HomeContract.State, HomeContract.Event, HomeContract.Effect> {

    data class State(
        val isLoading: Boolean = false,
        val nowTime: DateTime = DateTime.now(),
        val nowTag: String = "",
        val weekCount: Int = DateUtil.getMonthWithWeekCount(DateTime.now()),
        val weeklyList: List<WeeklyFeeds> = WeeklyFeeds.emptyWeeklyList,
        val feedList: List<HomeDailyFeed> = HomeDailyFeed.emptyListItem,
        val pageInfo: HomeListPaging? = HomeListPaging(),
        val nowTagList: List<String> = emptyList(),
        val tagCanGoPrevious: Boolean? = null,
        val tagCanGoNext: Boolean? = null,
    ) {
        val canPostOnToday : Boolean
            get() = nowTime.isBeforeNow
    }

    sealed interface Event {
        data class DeepLink(val link: String) : Event

        sealed interface Click : Event {
            object ChangeDate : Click
            object ListPage : Click
            object Setting : Click

            data class Feed(val id: String = "") : Click
            data class Date(val date: String) : Click
        }
    }

    sealed interface Effect {
        sealed interface Move : Effect {
            data class ListPage(val selectedDate : String) : Move
            object Setting : Move

            data class ChangeDate(val nowDate: DateTime = DateTime.now()) : Move
            data class DeepLink(val link: String) : Move
            data class Feed(val id: String, val postDate : String) : Move
        }
    }
}