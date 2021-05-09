package com.gratus.adidasconfirmed.data.service.local.pref

interface AppPrefHelper {
    fun getIpAddress(): String?

    fun setIpAddress(ipAddress: String)
}
