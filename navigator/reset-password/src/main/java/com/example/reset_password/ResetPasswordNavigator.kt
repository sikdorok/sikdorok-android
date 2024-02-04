package com.example.reset_password

import android.content.Context
import android.content.Intent
import com.ddd.sikdorok.navigator.core.Navigator

interface ResetPasswordNavigator : Navigator {
    fun start(context: Context, userId: String, code: String): Intent
}