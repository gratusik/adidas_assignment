package com.gratus.adidasconfirmed.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gratus.adidasconfirmed.di.factory.ViewModelFactory
import com.gratus.adidasconfirmed.di.key.ViewModelKey
import com.gratus.adidasconfirmed.ui.viewmodel.activity.MainViewModel
import com.gratus.adidasconfirmed.ui.viewmodel.activity.SplashViewModel
import com.gratus.adidasconfirmed.ui.viewmodel.fragment.ProductDetailsViewModel
import com.gratus.adidasconfirmed.ui.viewmodel.fragment.ProductListViewModel
import com.gratus.adidasconfirmed.ui.viewmodel.fragment.ReviewViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductListViewModel::class)
    abstract fun bindProductListViewModel(productListViewModel: ProductListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductDetailsViewModel::class)
    abstract fun bindProductDetailsViewModel(productDetailsViewModel: ProductDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReviewViewModel::class)
    abstract fun bindReviewViewModel(reviewViewModel: ReviewViewModel): ViewModel
}
