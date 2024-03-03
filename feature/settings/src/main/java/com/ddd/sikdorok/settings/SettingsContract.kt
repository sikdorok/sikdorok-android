package com.ddd.sikdorok.settings

class SettingsContract {
    data class State(
        val email: String = "",
        val nickname: String = "",
        val isNeedUpdate: Boolean = false,
        val isKakaoUser: Boolean = false
    )

    sealed class Event {
        object OnClickProfileManage : Event()

        object OnClickPolicy : Event()

        object OnClickAccountDelete : Event()

        object OnClickLogout : Event()

        object OnClickPlayStore : Event()
    }

    sealed class SideEffect {
        object NaviToProfileManage : SideEffect()

        object NaviToPolicy : SideEffect()

        object NaviToDeleteAccount : SideEffect()

        object Logout : SideEffect()

        object PlayStore : SideEffect()

        object NaviToSplash : SideEffect()

        data class Fail(val errorMsg: String) : SideEffect()
    }
}
