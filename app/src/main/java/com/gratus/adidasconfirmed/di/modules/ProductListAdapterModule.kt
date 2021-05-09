package com.gratus.adidasconfirmed.di.modules

import androidx.recyclerview.widget.LinearLayoutManager
import com.gratus.adidasconfirmed.ui.view.adapter.ProductListAdapter
import com.gratus.adidasconfirmed.ui.view.fragment.ProductListFragment
import dagger.Module
import dagger.Provides

@Module
class ProductListAdapterModule {
    @Provides
    fun provideProductListAdapter(): ProductListAdapter {
        return ProductListAdapter(ArrayList())
    }

    // layout manager for linear recycler view of product list fragment
    @Provides
    fun provideLinearLayoutManager(productListFragment: ProductListFragment): LinearLayoutManager {
        return LinearLayoutManager(productListFragment.activity)
    }
}
