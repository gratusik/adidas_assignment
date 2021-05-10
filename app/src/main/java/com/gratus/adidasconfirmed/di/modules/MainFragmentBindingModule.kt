package com.gratus.adidasconfirmed.di.modules

import com.gratus.adidasconfirmed.ui.view.fragment.ProductDetailsFragment
import com.gratus.adidasconfirmed.ui.view.fragment.ProductListFragment
import com.gratus.adidasconfirmed.ui.view.fragment.ReviewBottomModelFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

// main activity fragment binding 
@Module
abstract class MainFragmentBindingModule {
    @ContributesAndroidInjector(modules = [ProductListAdapterModule::class])
    abstract fun provideProductListFragment(): ProductListFragment

    @ContributesAndroidInjector(modules = [ReviewListAdapterModule::class])
    abstract fun provideProductDetailsFragment(): ProductDetailsFragment

    @ContributesAndroidInjector
    abstract fun provideReviewBottomModelFragment(): ReviewBottomModelFragment
}
