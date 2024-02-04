package com.ddd.sikdorok.data

@Suppress("SpellCheckingInspection")
interface SikdorokPreference {
    fun savePref(key: String, value: Any?)

    fun getString(key: String): String

    fun getBoolean(key: String): Boolean

    fun clearAllData() : Boolean
}
