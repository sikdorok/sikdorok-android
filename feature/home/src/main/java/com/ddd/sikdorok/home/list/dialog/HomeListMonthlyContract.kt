package com.ddd.sikdorok.home.list.dialog

import com.ddd.sikdorok.core_ui.base.BaseContract
import org.joda.time.DateTime

interface HomeListMonthlyContract :
    BaseContract<HomeListMonthlyContract.State, HomeListMonthlyContract.Event, HomeListMonthlyContract.Effect> {

    data class State(
        val selectedDate: String = DateTime.now().toString("yyyy-MM-dd"),
        val monthlyList: List<Pair<DateTime, String>> = emptyList()
    )

    sealed interface Event {
        data class ClickMonthDate(val date: String) : Event
        object ClickClose : Event
        object ClickConfirm : Event
    }

    sealed interface Effect {
        data class ChangeMonth(val date: DateTime) : Effect
        object Close : Effect
    }
}