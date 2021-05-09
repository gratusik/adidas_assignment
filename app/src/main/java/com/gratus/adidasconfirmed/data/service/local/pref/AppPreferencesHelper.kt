@file:Suppress("SpellCheckingInspection", "unused", "unused")

package com.gratus.adidasconfirmed.data.service.local.pref

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.gratus.adidasconfirmed.util.constants.AppConstants.Companion.APP_PREF_NAME
import com.gratus.adidasconfirmed.util.constants.AppConstants.Companion.IP_ADDRESS
import javax.inject.Inject

// Shared Preferences
class AppPreferencesHelper @Inject constructor(context: Context) : AppPrefHelper {
    private var gson = Gson()
    private var mPrefs: SharedPreferences =
        context.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE)

    fun isClear(): Boolean {
        return false
    }

    fun setClear(clear: Boolean) {
        val editor = mPrefs.edit()
        editor.clear()
        editor.apply()
    }

    // to get ip address
    override fun getIpAddress(): String? {
        return mPrefs.getString(IP_ADDRESS, null)
    }

    // to set ip address
    override fun setIpAddress(ipAddress: String) {
        mPrefs.edit().putString(IP_ADDRESS, ipAddress).apply()
    }
}
