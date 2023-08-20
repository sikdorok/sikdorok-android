package com.ddd.sikdorok.navigator.management

import android.content.Context
import android.content.Intent
import com.ddd.sikdorok.navigator.core.Navigator
import java.io.Serializable

interface ManagementNavigator : Navigator {

    data class Payload(
        val email: String,
        val nickname: String
    ): Serializable

    fun start(context: Context, payload: Payload): Intent
}
