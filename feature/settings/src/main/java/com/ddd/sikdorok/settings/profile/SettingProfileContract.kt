package com.ddd.sikdorok.settings.profile

class SettingProfileContract {
    data class State(
        val isLoading : Boolean = false,
        val name: String = "",
        val email: String = "",
    ) {
        val isValidate: Boolean
            get() {
                return name.isNotEmpty() && email.isNotEmpty()
            }
    }

    sealed class Event {
        data class NameCheck(val name: String) : Event()
        data class EditProfile(val name: String) : Event()

        object OnBackPressed : Event()
    }

    sealed class SideEffect {
        object InValidateName : SideEffect()
        object ValidateName : SideEffect()

        object NaviToBack : SideEffect()
        object NaviToSuccess : SideEffect()

        data class SnowSnackBar(val message: String) : SideEffect()
    }
}
