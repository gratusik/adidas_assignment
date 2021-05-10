package com.gratus.adidasconfirmed.ui.view.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.View
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.gratus.adidasconfirmed.R
import com.gratus.adidasconfirmed.util.constants.AppConstants
import com.gratus.adidasconfirmed.util.interceptor.AppInterceptor
import com.gratus.adidasconfirmed.util.networkManager.NetworkOnlineCheck
import com.gratus.adidasconfirmed.util.networkManager.NetworkOnlineReceiver
import com.gratus.adidasconfirmed.util.snackBar.CustomSnackBar
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var mInterceptor: AppInterceptor

    @Inject
    lateinit var networkOnlineCheck: NetworkOnlineCheck
    private var snackBar: Snackbar? = null
    private var initial: Boolean = false

    // Broadcast receiver to get network state
    private fun networkReceiver() {
        val myReceiver = NetworkOnlineReceiver(networkOnlineCheck)
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(myReceiver, filter)
    }

    // network check for internet connection
    internal fun networkCheck(view: View, bottomNavigation: BottomNavigationView?) {
        networkReceiver()
        val intentFilter =
            IntentFilter(AppConstants.NETWORK_AVAILABLE_ACTION)
        LocalBroadcastManager.getInstance(this).registerReceiver(
            object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent) {
                    val isNetworkAvailable =
                        intent.getBooleanExtra(AppConstants.IS_NETWORK_AVAILABLE, false)
                    if (!isNetworkAvailable) {
                        initial = true
                    }
                    if (initial) {
                        showSnack(isNetworkAvailable, view, bottomNavigation)
                        initial = isNetworkAvailable
                    }
                    initial = true
                }
            },
            intentFilter
        )
    }

    // show snack bar based on network connection boolean value
    internal fun showSnack(
        networkConnected: Boolean,
        parent: View?,
        bottomNavigation: BottomNavigationView?
    ) {
        if (networkConnected) {
            snackBar = Snackbar.make(parent!!, R.string.network_online, Snackbar.LENGTH_SHORT)
            CustomSnackBar().getSnackBarCustom(
                bottomNavigation,
                snackBar!!,
                snackBar!!.view,
                true,
                ContextCompat.getColor(applicationContext, R.color.black)
            )
        } else {
            snackBar = Snackbar.make(parent!!, R.string.network_offline, Snackbar.LENGTH_INDEFINITE)
            CustomSnackBar().getSnackBarCustom(
                bottomNavigation,
                snackBar!!,
                snackBar!!.view,
                false,
                ContextCompat.getColor(applicationContext, R.color.black)
            )
        }
    }
}
