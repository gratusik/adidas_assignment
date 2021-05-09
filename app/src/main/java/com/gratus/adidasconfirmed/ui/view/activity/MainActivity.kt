package com.gratus.adidasconfirmed.ui.view.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import com.gratus.adidasconfirmed.R
import com.gratus.adidasconfirmed.databinding.ActivityMainBinding
import com.gratus.adidasconfirmed.ui.view.base.BaseActivity
import com.gratus.adidasconfirmed.ui.viewmodel.activity.MainViewModel
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        activityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        activityMainBinding.mainViewModel = mainViewModel
        activityMainBinding.lifecycleOwner = this
        // network check using receiver
        networkCheck(activityMainBinding.parentMain, null)
    }

    // nav host to move from fragment to fragment
    override fun onSupportNavigateUp() =
        findNavController(this, R.id.nav_host_fragment).navigateUp()
}
