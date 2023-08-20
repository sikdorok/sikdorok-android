package com.ddd.sikdorok.settings

class SettingsContract {
    data class State(
        val email: String = "",
        val nickname: String = ""
    )

    sealed class Event {
        object OnClickProfileManage : Event()

        object OnClickPolicy : Event()

        object OnClickAccountDelete : Event()
    }

    sealed class SideEffect {
        object NaviToProfileManage : SideEffect()

        object NaviToPolicy : SideEffect()

        object NaviToDeleteAccount : SideEffect()
    }
}
