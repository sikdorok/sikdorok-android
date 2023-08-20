package com.ddd.sikdorok.navigator

import android.content.Context
import android.content.Intent
import com.ddd.sikdorok.delete_account.DeleteAccountActivity
import com.ddd.sikdorok.navigator.delete_account.DeleteAccountNavigator
import javax.inject.Inject

internal class DeleteAccountNavigatorImpl @Inject constructor(): DeleteAccountNavigator {
    override fun start(context: Context): Intent {
        return Intent(context, DeleteAccountActivity::class.java)
    }
}
