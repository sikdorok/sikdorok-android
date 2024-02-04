package com.ddd.sikdorok.preference

import android.content.Context
import com.ddd.sikdorok.data.SikdorokPreference
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.lang.IllegalArgumentException
import javax.inject.Inject

@Suppress("SpellCheckingInspection")
internal class SikdorokPrefernceImpl @Inject constructor(
    @ApplicationContext context: Context
): SikdorokPreference {

    private val preference = context.getSharedPreferences("com.ddd.sikdorok", Context.MODE_PRIVATE)

    override fun savePref(key: String, value: Any?) {
        val edit = preference.edit()
        when(value) {
            is String -> {
                edit.putString(key, value)
            }
            is Boolean -> {
                edit.putBoolean(key, value)
            }
            else -> throw IllegalArgumentException("value 는 null 값이 들어올 수 없습니다.")
        }

        edit.apply()
    }

    override fun getString(key: String): String {
        return preference.getString(key, "").orEmpty()
    }

    override fun getBoolean(key: String): Boolean {
        return preference.getBoolean(key, false)
    }

    override fun clearAllData(): Boolean {
        preference.edit().clear().apply()
        return true
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PreferenceModule {
    @Binds
    abstract fun bindsPreference(
        pref: SikdorokPrefernceImpl
    ): SikdorokPreference
}
