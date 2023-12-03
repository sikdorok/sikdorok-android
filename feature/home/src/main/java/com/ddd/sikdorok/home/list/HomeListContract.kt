package com.ddd.sikdorok.home.list

import com.ddd.sikdorok.core_ui.base.BaseContract
import com.ddd.sikdorok.shared.home.DailyFeed
import org.joda.time.DateTime

interface HomeListContract :
    BaseContract<HomeListContract.State, HomeListContract.Event, HomeListContract.Effect> {

    data class State(
        val isLoading: Boolean = false,
        val nowTime: DateTime = DateTime.now(),
        val feedList: List<DailyFeed> = emptyList(),
        val canGoPrevious: Boolean = false,
        val canGoNext: Boolean = false,
    )

    sealed interface Event {
        sealed interface Click : Event {
            object ChangeDate : Click

            data class Feed(val id: String) : Click
        }
    }

    sealed interface Effect {
        sealed interface Move : Effect {
            data class ChangeDate(val nowDate: DateTime = DateTime.now()) : Move
            data class Feed(val id: String) : Move
        }
    }
}