package com.ddd.sikdorok.modify

import java.io.Serializable

sealed class PickerData : Serializable {
    data class Date(val year: Int, val month: Int, val day: Int) : PickerData()

    data class Time(val hours: String, val minute: Int) : PickerData()
}
