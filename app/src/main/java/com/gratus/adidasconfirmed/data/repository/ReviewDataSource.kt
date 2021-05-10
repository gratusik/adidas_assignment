package com.gratus.adidasconfirmed.data.repository

import com.gratus.adidasconfirmed.data.service.remote.ReviewService
import com.gratus.adidasconfirmed.domain.model.remoteResponse.Review
import com.gratus.adidasconfirmed.domain.repository.ReviewRepository
import io.reactivex.Single
import javax.inject.Inject

// data source which hits the service api
class ReviewDataSource @Inject constructor(
    private var reviewService: ReviewService
) : ReviewRepository {

    // add review to remote server
    override fun addReview(review: Review): Single<Review> {
        return reviewService.addReviewService(review.productId!!, review)
    }
}
