package com.gratus.adidasconfirmed.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.gratus.adidasconfirmed.R
import com.gratus.adidasconfirmed.databinding.ActivitySplashBinding
import com.gratus.adidasconfirmed.ui.view.base.BaseActivity
import com.gratus.adidasconfirmed.ui.viewmodel.activity.SplashViewModel
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    private lateinit var activitySplashBinding: ActivitySplashBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var splashViewModel: SplashViewModel

    private val splashDisplayLength: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        activitySplashBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_splash)
        splashViewModel =
            ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)
        activitySplashBinding.splashViewModel = splashViewModel
        activitySplashBinding.lifecycleOwner = this
        // move to Main page after 1 second delay
        Handler(Looper.getMainLooper()).postDelayed(
            {
                intentMainActivity()
            },
            splashDisplayLength
        )
    }

    private fun intentMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
