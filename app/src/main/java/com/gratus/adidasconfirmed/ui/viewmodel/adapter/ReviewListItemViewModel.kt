package com.gratus.adidasconfirmed.ui.viewmodel.adapter

import com.gratus.adidasconfirmed.domain.model.remoteResponse.Review
import javax.inject.Inject

// Item view model to data binding of data to respective xml
class ReviewListItemViewModel @Inject constructor(
    private var review: Review,
) {
    fun getReviewItem(): Review {
        return review
    }
}
