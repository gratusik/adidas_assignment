package com.gratus.adidasconfirmed.di.modules

import com.gratus.adidasconfirmed.ui.view.adapter.ProductListAdapter
import dagger.Module
import dagger.Provides

@Module
class ProductListAdapterModule {
    @Provides
    fun provideProductListAdapter(): ProductListAdapter {
        return ProductListAdapter(ArrayList())
    }
}
