package com.gratus.adidasconfirmed.di.modules

import com.gratus.adidasconfirmed.ui.view.activity.MainActivity
import com.gratus.adidasconfirmed.ui.view.activity.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [MainFragmentBindingModule::class])
    abstract fun bindMainActivity(): MainActivity
}
