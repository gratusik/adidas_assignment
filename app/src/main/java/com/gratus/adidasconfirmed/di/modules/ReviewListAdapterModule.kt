package com.gratus.adidasconfirmed.di.modules

import com.gratus.adidasconfirmed.ui.view.adapter.ReviewListAdapter
import dagger.Module
import dagger.Provides

@Module
class ReviewListAdapterModule {
    @Provides
    fun provideReviewListAdapter(): ReviewListAdapter {
        return ReviewListAdapter(ArrayList())
    }
}
