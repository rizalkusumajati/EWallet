package com.riztech.ewallet.data.local.sharedpref

import android.content.SharedPreferences

object EwalletPref {

    val USER_ID = "USER_ID"

    inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.userId
        get() = getString(USER_ID, "")
        set(value) {
            editMe {
                it.putString(USER_ID, value)
            }
        }


    var SharedPreferences.clearValues
        get() = { }
        set(value) {
            editMe {
                it.clear()
            }
        }
}