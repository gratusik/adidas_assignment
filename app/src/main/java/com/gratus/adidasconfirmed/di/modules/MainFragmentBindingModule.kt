package com.gratus.adidasconfirmed.di.modules

import com.gratus.adidasconfirmed.ui.view.fragment.ProductDetailsFragment
import com.gratus.adidasconfirmed.ui.view.fragment.ProductListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

// main activity fragment binding 
@Module
abstract class MainFragmentBindingModule {
    @ContributesAndroidInjector(modules = [ProductListAdapterModule::class])
    abstract fun provideProductListFragment(): ProductListFragment

    @ContributesAndroidInjector
    abstract fun provideProductDetailsFragment(): ProductDetailsFragment
}
